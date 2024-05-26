package com.test.tutipet.exception;

public class BadRequestException extends CustomException {


    public BadRequestException(String code, String msg) {
        super(code, msg);
    }
}
