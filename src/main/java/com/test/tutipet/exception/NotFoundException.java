package com.test.tutipet.exception;

public class NotFoundException extends CustomException{


    public NotFoundException(String code, String msg) {
        super(code, msg);
    }
}
