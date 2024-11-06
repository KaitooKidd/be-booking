package com.booking.auth.exception;

import org.springframework.http.HttpStatus;

public class AuthorizationException extends BusinessException {
    public AuthorizationException(String message) {
        this.message = message;
        this.status = HttpStatus.UNAUTHORIZED;
    }
}
