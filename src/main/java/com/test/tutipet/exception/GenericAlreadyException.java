package com.test.tutipet.exception;


import lombok.Getter;

@Getter
public class GenericAlreadyException extends CustomException{

    public GenericAlreadyException(ErrorCode code) {
        super(code.getErrMessage());
        this.errCode = code.getErrCode();
        this.errMsg = code.getErrMessage();
    }

    public GenericAlreadyException(final String errMsg) {
        super(errMsg);
        this.errMsg = ErrorCode.RESOURCE_NOT_FOUND.getErrMessage();
        this.errCode = ErrorCode.RESOURCE_NOT_FOUND.getErrCode();
    }

}
