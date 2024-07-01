package com.test.tutipet.dtos.users;

import com.test.tutipet.enums.Role;

public record UserRes(
        Long id,
        String name,
        String email,
        boolean gender,
        Role role
) {
}
