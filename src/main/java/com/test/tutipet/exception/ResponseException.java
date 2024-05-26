package com.test.tutipet.exception;

public record ResponseException(
        String status,
        String msg
) {
}
