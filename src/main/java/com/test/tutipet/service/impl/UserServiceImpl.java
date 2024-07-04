package com.test.tutipet.service.impl;

import com.test.tutipet.constants.MessageException;
import com.test.tutipet.converter.UserDtoConverter;
import com.test.tutipet.dtos.PageRes;
import com.test.tutipet.dtos.users.CreateUserReq;
import com.test.tutipet.dtos.users.UpdateUserReq;
import com.test.tutipet.dtos.users.UserRes;
import com.test.tutipet.entity.User;
import com.test.tutipet.enums.ObjectStatus;
import com.test.tutipet.exception.BadRequestException;
import com.test.tutipet.exception.NotFoundException;
import com.test.tutipet.repository.UserRepository;
import com.test.tutipet.service.UserService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@Transactional
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public List<UserRes> getAllUsers() {
        return UserDtoConverter.toResponseList(userRepository.findAll());
    }

    @Override
    public PageRes<UserRes> searchAllUsers(
            String keySearch, int page, int size, String sortBy, String sortDir) {

        final Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ?
                Sort.by(sortBy).ascending() :
                Sort.by(sortBy).descending();

        final Pageable pageable = PageRequest.of(page, size, sort);

        final Page<User> users = userRepository
                .findByFullNameContainingAndObjectStatus(keySearch,pageable,ObjectStatus.ACTIVE);

        final List<UserRes> userResList = users
                .stream()
                .map(UserDtoConverter::toResponse)
                .toList();

        return new PageRes<>(
                userResList,
                users.getNumber(),
                users.getSize(),
                users.getTotalElements(),
                users.getTotalPages(),
                users.isLast()
        );
    }

    @Override
    public UserRes getUserById(long id) {
        return UserDtoConverter.toResponse(userRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("User Not Found With id" + id)));
    }

    @Override
    public UserRes updateUser(long id, UpdateUserReq req) {
        final User user = userRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("User Not Found With id" + id));

        final User newUser = UserDtoConverter.toEntity(req,user);

        updateDeletedUserData(user);
        userRepository.save(newUser);
        return UserDtoConverter.toResponse(userRepository.save(user));
    }

    @Override
    public void deleteUser(long id) {
        final User user = userRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("User Not Found With id" + id));

        updateDeletedUserData(user);

        userRepository.save(user);
    }

    @Override
    public UserRes createUser(CreateUserReq userReq) {
        final boolean existEmail = userRepository.existsByEmail(userReq.getEmail());
        if (existEmail) {
            throw new BadRequestException(MessageException.ALREADY_EXIST_EMAIL);
        }

        final User user = UserDtoConverter.toEntity(userReq);

        user.setPassword(passwordEncoder.encode(userReq.getPassword()));

        return UserDtoConverter.toResponse(userRepository.save(user));
    }


    private void updateDeletedUserData(User user){
        user.setObjectStatus(ObjectStatus.DELETED);
        user.setEmail("deleted-" + UUID.randomUUID() + user.getEmail());
    }

}
