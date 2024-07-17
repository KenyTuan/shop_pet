package com.test.tutipet.service.impl;

import com.test.tutipet.constants.MessageException;
import com.test.tutipet.converter.AuthDtoConverter;
import com.test.tutipet.converter.UserDtoConverter;
import com.test.tutipet.dtos.auth.*;
import com.test.tutipet.dtos.users.ChangePasswordReq;
import com.test.tutipet.dtos.users.UpdateUserReq;
import com.test.tutipet.dtos.users.UserRes;
import com.test.tutipet.entity.Cart;
import com.test.tutipet.entity.User;
import com.test.tutipet.enums.ObjectStatus;
import com.test.tutipet.exception.BadRequestException;
import com.test.tutipet.exception.GenericAlreadyException;
import com.test.tutipet.exception.NotFoundException;
import com.test.tutipet.repository.CartRepository;
import com.test.tutipet.repository.UserRepository;
import com.test.tutipet.security.JwtUtil;
import com.test.tutipet.service.AuthService;
import com.test.tutipet.service.MailService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.scheduling.annotation.Async;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.*;


@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private static final int EXPIRATION = 60 * 15 *1000;

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final CartRepository cartRepository;
    private final JwtUtil jwtUtil;
    private final AuthenticationManager authenticationManager;
    private final MailService mailService;


    @Override
    @Transactional
    public AuthRes register(RegisterReq registerReq) {

        final boolean existedEmail = userRepository.existsByEmail(registerReq.getEmail());

        if (existedEmail) {
            throw new GenericAlreadyException(MessageException.ALREADY_EXIST_EMAIL);
        }


        final User user = AuthDtoConverter.toEntity(registerReq);
        user.setPassword(passwordEncoder.encode(registerReq.getPassword()));

        final User newUser = userRepository.save(user);
        Cart cart = Cart.builder().user(newUser).build();
        cartRepository.save(cart);

        Date issuedAt = jwtUtil.getIssuedAtDate();
        Date expirationAt = jwtUtil.getExpirationDate();


        return AuthDtoConverter
                .toResponse(
                        jwtUtil.generateToken(user,issuedAt,expirationAt),
                        issuedAt,
                        expirationAt,
                        user
                );
    }

    @Override
    @Transactional
    public AuthRes authenticate(AuthReq authReq) {

        final User user = userRepository.findByEmail(authReq.getEmail())
                .orElseThrow(() -> new NotFoundException(MessageException.NOT_FOUND_USER));

        if (!passwordEncoder.matches(authReq.getPassword(), user.getPassword())){
            throw new BadRequestException(MessageException.NOT_MATCH_PASSWORD);
        }

        authenticationManager
                .authenticate(
                        new UsernamePasswordAuthenticationToken(authReq.getEmail(),
                                authReq.getPassword()));

        Date issuedAt = jwtUtil.getIssuedAtDate();
        Date expirationAt = jwtUtil.getExpirationDate();

        return AuthDtoConverter
                .toResponse(
                        jwtUtil.generateToken(user,issuedAt,expirationAt),
                        issuedAt,
                        expirationAt,
                        user
                );
    }

    @Override
    @Transactional
    public void requestForget(String email) {
        final User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new NotFoundException(MessageException.NOT_FOUND_USER));

        final String token = UUID.randomUUID().toString();

        user.setToken(token);
        user.setExpiryDate(new Timestamp(System.currentTimeMillis() + EXPIRATION));

        userRepository.save(user);

        String subject = "Verification Code";
        String body = "Hi, " + user.getFullName() + "\n\nYour Verification Code: " + token;

        mailService.sendMail(email,subject, body);
    }

    @Override
    @Transactional
    public void forgetPassword(ForgotReq forgotReq) {
        final User user = userRepository.findByEmailAndToken(forgotReq.getEmail(), forgotReq.getToken())
                .orElseThrow(() -> new NotFoundException(MessageException.NOT_FOUND_USER));

        if(user.isTokenExpired()){
            throw new BadRequestException(MessageException.TOKEN_EXPIRED);
        }

        final User newUser = new User();
        BeanUtils.copyProperties(user, newUser);

        updateDeletedUserData(user);

        newUser.setExpiryDate(new Timestamp(System.currentTimeMillis()));
        newUser.setPassword(passwordEncoder.encode(forgotReq.getPassword()));
        userRepository.save(newUser);

        String subject = "Password Reset";
        String body = "Hi, " + user.getFullName() + "\n\nCompleted Password Reset";

        mailService.sendMail(user.getEmail(),subject, body);
    }

    @Override
    @Transactional
    public void changePasswordByToken(String token, ChangePasswordReq changePasswordReq) {
        final User user = getUserByEmailFormToken(token);

        if (passwordEncoder.matches(changePasswordReq.getOldPassword(), user.getPassword())) {
            throw new BadRequestException(MessageException.NOT_MATCH_PASSWORD);
        }

        user.setPassword(passwordEncoder.encode(changePasswordReq.getNewPassword()));
        userRepository.save(user);
    }

    @Override
    @Transactional
    public UserRes updateProfileByToken(String token, UpdateUserReq updateUserReq) {
        final User user = getUserByEmailFormToken(token);

        final User updatedUser = UserDtoConverter.toEntity(updateUserReq,user);

        updateDeletedUserData(user);

        userRepository.save(updatedUser);

        return UserDtoConverter.toResponse(updatedUser);
    }

    public User getUserByEmailFormToken(String token) {
        final String email = jwtUtil.extractUsername(token.substring(7));
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new NotFoundException(MessageException.NOT_FOUND_USER));
    }

    @Async
    public void updateDeletedUserData(User user){
        user.setObjectStatus(ObjectStatus.DELETED);
        user.setEmail("deleted-" + UUID.randomUUID() + user.getEmail());
        userRepository.save(user);

    }


}
