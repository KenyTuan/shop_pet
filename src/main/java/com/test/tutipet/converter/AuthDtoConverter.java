package com.test.tutipet.converter;

import com.test.tutipet.dtos.auth.AuthRes;
import com.test.tutipet.dtos.auth.RegisterReq;
import com.test.tutipet.entity.User;
import com.test.tutipet.enums.ObjectStatus;
import com.test.tutipet.enums.Role;

import java.util.Date;

public class AuthDtoConverter {

    public static AuthRes toResponse(String token,Date issueAt ,Date expAt){
        return new AuthRes(
                token,
                issueAt,
                expAt
        );
    }

    public static User toEntity(RegisterReq req){
        User user = User
                .builder()
                .fullName(req.getFullName())
                .email(req.getEmail())
                .gender(req.isGender())
                .role(Role.USER)
                .build();
        user.setObjectStatus(ObjectStatus.ACTIVE);
        return user;
    }





}
