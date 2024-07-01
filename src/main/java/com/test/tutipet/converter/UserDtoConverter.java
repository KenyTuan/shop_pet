package com.test.tutipet.converter;

import com.test.tutipet.dtos.users.UserReq;
import com.test.tutipet.dtos.users.UserRes;
import com.test.tutipet.entity.User;
import com.test.tutipet.enums.ObjectStatus;

public class UserDtoConverter {

    public static User toEntity(UserReq req){
        User user = User.builder()
                .fullName(req.getFullName())
                .gender(req.isGender())
                .build();
        user.setObjectStatus(ObjectStatus.ACTIVE);
        return user;
    }

    public static UserRes toResponse(User user){
        return new UserRes(
                user.getId(),
                user.getFullName(),
                user.getEmail(),
                user.isGender(),
                user.getRole()
        );
    }

}
