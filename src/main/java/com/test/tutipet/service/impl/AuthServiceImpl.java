package com.test.tutipet.service.impl;

import com.test.tutipet.converter.AuthDtoConverter;
import com.test.tutipet.dtos.auth.AuthReq;
import com.test.tutipet.dtos.auth.AuthRes;
import com.test.tutipet.dtos.auth.RegisterReq;
import com.test.tutipet.entity.Cart;
import com.test.tutipet.entity.User;
import com.test.tutipet.exception.NotFoundException;
import com.test.tutipet.repository.CartRepo;
import com.test.tutipet.repository.UserRepo;
import com.test.tutipet.security.JwtUtil;
import com.test.tutipet.service.AuthService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@Transactional
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserRepo userRepo;
    private final PasswordEncoder passwordEncoder;
    private final CartRepo cartRepo;
    private final JwtUtil jwtUtil;
    private final AuthenticationManager authenticationManager;


    @Override
    public AuthRes register(RegisterReq registerReq) {

        final boolean existedEmail = userRepo.existsByEmail(registerReq.getEmail());

        if (existedEmail) {
            throw new NotFoundException("404","Email Already Exists");
        }


        final User user = AuthDtoConverter.toEntity(registerReq);
        user.setPassword(passwordEncoder.encode(registerReq.getPassword()));

        final User newUser = userRepo.save(user);
        Cart cart = Cart.builder().user(newUser).build();
        cartRepo.save(cart);

        return AuthDtoConverter.toResponse(jwtUtil.generateToken(user),604800000L);
    }

    @Override
    public AuthRes authenticate(AuthReq authReq) {

        final User user = userRepo.findByEmail(authReq.getEmail())
                .orElseThrow(() -> new NotFoundException("404","Email Not Found"));

        authenticationManager
                .authenticate(
                        new UsernamePasswordAuthenticationToken(authReq.getEmail(),
                                authReq.getPassword()));

        return AuthDtoConverter.toResponse(jwtUtil.generateToken(user),604800000L);
    }


}
