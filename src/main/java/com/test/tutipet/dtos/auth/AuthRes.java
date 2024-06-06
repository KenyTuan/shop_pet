package com.test.tutipet.dtos.auth;

import java.util.Date;

public record AuthRes(
        String token,
        Date issuedAt,
        Date expAt
) {
}
