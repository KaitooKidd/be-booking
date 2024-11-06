package com.booking.auth.constant;

import org.springframework.beans.factory.annotation.Value;

public class RequestConstant {
    public static final String AUTH_HEADER = "Authorization";
    public static final String AUTH_TOKEN_PREFIX = "Bearer ";

    @Value("${jwt.authSecret}")
    public static String AUTH_SECRET;

    @Value("${jwt.valid-duration}")
    public static long JWT_EXPIRATION;

}
