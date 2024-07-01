package com.test.tutipet.service.impl;

import com.test.tutipet.converter.UserDtoConverter;
import com.test.tutipet.dtos.PageRes;
import com.test.tutipet.dtos.users.UserReq;
import com.test.tutipet.dtos.users.UserRes;
import com.test.tutipet.entity.User;
import com.test.tutipet.enums.ObjectStatus;
import com.test.tutipet.exception.GenericAlreadyException;
import com.test.tutipet.exception.NotFoundException;
import com.test.tutipet.repository.UserRepository;
import com.test.tutipet.service.UserService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@Transactional
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    public PageRes<UserRes> getAllUser(
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
    public UserRes updateUser(long id, UserReq req) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("User Not Found With id" + id));

        user.setFullName(req.getFullName());
        user.setGender(req.isGender());

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
    public UserRes createUser(UserReq userReq) {

        final User user = UserDtoConverter.toEntity(userReq);

        return UserDtoConverter.toResponse(userRepository.save(user));
    }


    private void updateDeletedUserData(User user){
        user.setObjectStatus(ObjectStatus.DELETED);
        user.setEmail("deleted-" + UUID.randomUUID() + user.getEmail());
    }

}
