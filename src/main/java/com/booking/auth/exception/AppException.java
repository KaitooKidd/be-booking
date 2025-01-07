package com.booking.auth.exception;

public class AppException extends RuntimeException {

    public AppException(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }

    public AppException(String message, ErrorCode errorCode) {
        super(errorCode.formatMessage(message));
        this.errorCode = errorCode;
    }

    private final ErrorCode errorCode;

    public ErrorCode getErrorCode() {
        return errorCode;
    }

}
