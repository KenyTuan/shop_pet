package com.test.tutipet.exception;

import java.time.Instant;

public record ResponseException(
        String status,
        String msg
) {
}
