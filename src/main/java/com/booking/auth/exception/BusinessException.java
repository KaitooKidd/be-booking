package com.booking.auth.exception;

import org.springframework.http.HttpStatus;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BusinessException extends RuntimeException {

    protected HttpStatus status;
    protected String message;
    protected String detailMessage; // nullable
    protected Object objectMessage;

    public BusinessException(HttpStatus status, String message) {
        this.status = status;
        this.message = message;
    }

    public static BusinessException badRequest(String message) {
        return new BusinessException(HttpStatus.BAD_REQUEST, message);
    }
}
