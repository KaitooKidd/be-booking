package com.booking.auth.exception;

import org.springframework.http.HttpStatus;

public class SendEmailFailureException extends BusinessException {
    public SendEmailFailureException(String email) {
        this.status = HttpStatus.INTERNAL_SERVER_ERROR;
        this.message = "Can't send email to " + email;
    }
}
