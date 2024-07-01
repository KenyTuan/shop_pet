package com.test.tutipet.exception;

import java.io.Serializable;
import java.time.Instant;

public record ResponseException(
        String code,
        String message,
        Integer status,
        String url,
        String reqMethod,
        Instant timestamp
) implements Serializable {
}
