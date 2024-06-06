package com.test.tutipet.exception;

public class ResourceNotFoundException extends CustomException{
    public ResourceNotFoundException(String message) {
        super(message);
        this.errCode = ErrorCode.RESOURCE_NOT_FOUND.getErrCode();
        this.errMsg = ErrorCode.RESOURCE_NOT_FOUND.getErrMessage();
    }

    public ResourceNotFoundException(ErrorCode errCode) {
        super(errCode.getErrCode());
        this.errCode = errCode.getErrCode();
        this.errMsg = errCode.getErrMessage();
    }
}
