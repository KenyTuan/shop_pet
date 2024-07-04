package com.test.tutipet.converter;

import com.test.tutipet.dtos.users.CreateUserReq;
import com.test.tutipet.dtos.users.UpdateUserReq;
import com.test.tutipet.dtos.users.UserRes;
import com.test.tutipet.entity.User;
import com.test.tutipet.enums.ObjectStatus;
import com.test.tutipet.enums.Role;
import org.springframework.beans.BeanUtils;

import java.util.List;

public class UserDtoConverter {

    public static User toEntity(UpdateUserReq req){
        User user = User.builder()
                .fullName(req.getFullName())
                .gender(req.getGender())
                .build();
        user.setObjectStatus(ObjectStatus.ACTIVE);
        return user;
    }

    public static UserRes toResponse(User user){
        return new UserRes(
                user.getId(),
                user.getFullName(),
                user.getEmail(),
                user.getGender(),
                user.getRole()
        );
    }

    public static User toEntity(UpdateUserReq req, User oldUser){
        final User user = new User();
        BeanUtils.copyProperties(oldUser, user);

        user.setFullName(req.getFullName());
        user.setGender(req.getGender());
        return user;
    }

    public static User toEntity(CreateUserReq req) {
        User user = User.builder()
                .email(req.getEmail())
                .fullName(req.getFullName())
                .gender(req.getGender())
                .role(Role.MANAGER)
                .build();
        user.setObjectStatus(ObjectStatus.ACTIVE);
        return user;
    }

    public static List<UserRes> toResponseList(List<User> users) {
        return users.stream().map(UserDtoConverter::toResponse).toList();
    }
}
