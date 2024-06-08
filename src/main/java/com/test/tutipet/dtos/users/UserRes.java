package com.test.tutipet.dtos.users;

import com.test.tutipet.enums.ObjectStatus;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.RepresentationModel;

import java.time.LocalDateTime;

public record UserRes(
        Long id,
        String name,
        String email,
        boolean gender,
        LocalDateTime createdAt,
        Long createdBy,
        LocalDateTime updatedAt,
        Long updatedBy,
        ObjectStatus objectStatus
) {
}
