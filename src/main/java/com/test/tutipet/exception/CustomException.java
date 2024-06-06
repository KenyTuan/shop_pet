package com.test.tutipet.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.io.Serial;

@AllArgsConstructor
@Getter
public class CustomException extends RuntimeException {

    @Serial
    private static final long serialVersionUID = 1L;

    public String errCode;

    public String errMsg;

    public CustomException(String message) {
        super(message);
    }
}
