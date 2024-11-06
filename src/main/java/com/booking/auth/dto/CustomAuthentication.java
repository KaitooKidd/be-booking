package com.booking.auth.dto;

import com.booking.users.dtos.request.UserRequest;
import org.springframework.security.authentication.AbstractAuthenticationToken;

public class CustomAuthentication extends AbstractAuthenticationToken {
    private final String token;
    private final UserRequest userRequest;

    public CustomAuthentication(String token, UserRequest userRequest) {
        super(null);
        this.token = token;
        this.userRequest = userRequest;
        setAuthenticated(true);
    }

    @Override
    public Object getCredentials() {
        return token;
    }

    @Override
    public Object getPrincipal() {
        return userRequest;
    }
}
