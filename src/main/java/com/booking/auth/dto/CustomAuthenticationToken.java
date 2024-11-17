package com.booking.auth.dto;

import java.util.List;

import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

import com.booking.users.dtos.request.UserRequest;

public class CustomAuthenticationToken extends AbstractAuthenticationToken {
    private String token;
    private UserRequest userRequest;

    public CustomAuthenticationToken(List<GrantedAuthority> authorities) {
        super(authorities);
    }

    public CustomAuthenticationToken(String token, UserRequest userRequest, List<GrantedAuthority> authorities) {
        super(authorities);
        this.token = token;
        this.userRequest = userRequest;
        //        setAuthenticated(true);
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
