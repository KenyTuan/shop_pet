package com.test.tutipet.exception;

public class InvalidRefreshTokenException extends CustomException{

    public InvalidRefreshTokenException(final String message) {
        super(message);
        this.errMsg = ErrorCode.UNAUTHORIZED.getErrMessage();
        this.errCode = ErrorCode.UNAUTHORIZED.getErrCode();
    }

    public InvalidRefreshTokenException(ErrorCode errorCode) {
        super(errorCode.getErrMessage());
        this.errMsg = errorCode.getErrMessage();
        this.errCode = errorCode.getErrCode();
    }

}
