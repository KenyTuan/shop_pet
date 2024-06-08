package com.test.tutipet.exception;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class CustomException extends RuntimeException {

    public String code;

    public String msg;
}
