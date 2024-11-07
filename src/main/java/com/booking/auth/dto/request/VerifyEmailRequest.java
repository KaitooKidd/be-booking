package com.booking.auth.dto.request;

import lombok.Data;

@Data
public class VerifyEmailRequest {
    private String verifyToken;
}
