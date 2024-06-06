package com.test.tutipet.converter;

import com.test.tutipet.dtos.products.ProductReq;
import com.test.tutipet.dtos.products.ProductRes;
import com.test.tutipet.dtos.users.UserReq;
import com.test.tutipet.dtos.users.UserRes;
import com.test.tutipet.entity.Product;
import com.test.tutipet.entity.User;
import com.test.tutipet.enums.EnableStatus;
import com.test.tutipet.enums.ObjectStatus;
import com.test.tutipet.enums.Role;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class UserDtoConverter {

    public static User toEntity(UserReq req){
        User user = User.builder()
                .fullName(req.getFullName())
                .email(req.getEmail())
                .password(new BCryptPasswordEncoder().encode(req.getPassword()))
                .gender(req.isGender())
                .role(Role.MANAGER)
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
                user.getCreatedAt(),
                user.getCreatedBy(),
                user.getUpdatedAt(),
                user.getUpdatedBy(),
                user.getObjectStatus()
        );
    }

}
