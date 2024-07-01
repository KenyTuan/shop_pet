package com.test.tutipet.exception;

public class BadRequestException extends CustomException {

    public BadRequestException(String msg) {
        super("400", msg);
    }

    public BadRequestException(String code, String msg) {
        super(code, msg);
    }
}
