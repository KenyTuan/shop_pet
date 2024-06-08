package com.test.tutipet.dtos.auth;

import com.test.tutipet.dtos.users.UserRes;

import java.util.Date;

public record AuthRes(
        String token,
        Date issuedAt,
        Date expAt,
        UserRes user
) {
}
