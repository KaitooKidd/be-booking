package com.booking.auth.filter;

import com.booking.auth.constant.RequestConstant;
import com.booking.auth.dto.CustomAuthentication;
import com.booking.auth.service.FirebaseAuthService;
import com.booking.users.dtos.request.UserRequest;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;


@Component
@RequiredArgsConstructor
public class FirebaseRequestFilter extends OncePerRequestFilter {
    private final FirebaseAuthService firebaseAuthService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String token = request.getHeader(RequestConstant.AUTH_HEADER);
        UserRequest userRequest = firebaseAuthService.authenticate(token);
        CustomAuthentication customAuthentication = new CustomAuthentication(token, userRequest);
        SecurityContextHolder.getContext().setAuthentication(customAuthentication);
        filterChain.doFilter(request, response);
    }
}
