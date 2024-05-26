package com.test.tutipet.dtos.auth;

public record AuthRes(
        String token,
        long expTime
) {
}
