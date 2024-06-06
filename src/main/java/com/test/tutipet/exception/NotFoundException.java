package com.test.tutipet.exception;

public class NotFoundException extends CustomException{

    public NotFoundException(final String message) {
        super(message);
        this.errMsg = ErrorCode.CUSTOMER_NOT_FOUND.getErrMessage();
        this.errCode = ErrorCode.CUSTOMER_NOT_FOUND.getErrCode();
    }

    public NotFoundException(ErrorCode errorCode) {
        super(errorCode.getErrMessage());
        this.errMsg = errorCode.getErrMessage();
        this.errCode = errorCode.getErrCode();
    }
}
