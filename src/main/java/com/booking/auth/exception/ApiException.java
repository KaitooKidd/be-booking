package com.booking.auth.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class ApiException extends RuntimeException {
    private int code;
    private String method;
    private String url;
    private String message;
    private String traceId;

    public ApiException(int code, String message) {
        this.message = message;
        this.code = code;
    }
}
