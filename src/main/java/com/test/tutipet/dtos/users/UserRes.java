package com.test.tutipet.dtos.users;

import com.test.tutipet.enums.GenderType;
import com.test.tutipet.enums.Role;

public record UserRes(
        Long id,
        String fullName,
        String email,
        GenderType gender,
        Role role
) {
}
