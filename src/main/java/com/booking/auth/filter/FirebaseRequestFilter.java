package com.booking.auth.filter;

import com.booking.auth.constant.RequestConstant;
import com.booking.auth.dto.CustomAuthenticationToken;
import com.booking.auth.service.FirebaseAuthService;
import com.booking.users.dtos.request.UserRequest;
import com.booking.users.entity.UserEntity;
import com.booking.users.service.UserService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;


@Component
@RequiredArgsConstructor
public class FirebaseRequestFilter extends OncePerRequestFilter {
    private final FirebaseAuthService firebaseAuthService;
    private final UserService userService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String token = request.getHeader(RequestConstant.AUTH_HEADER);
        UserRequest userRequest = firebaseAuthService.authenticate(token);

        UserEntity userEntity = userService.getUserByEmail(userRequest.getEmail(), null);
        List<GrantedAuthority> authorities = AuthorityUtils.NO_AUTHORITIES;
        if (userEntity != null) {
            if (userEntity.getRole() != null) {
                authorities = AuthorityUtils.createAuthorityList(userEntity.getRole().getName());
            }

        }

            CustomAuthenticationToken customAuthenticationToken = new CustomAuthenticationToken(token, userRequest, authorities);
            SecurityContextHolder.getContext().setAuthentication(customAuthenticationToken);
            SecurityContextHolder.getContext().getAuthentication().setAuthenticated(true);
        filterChain.doFilter(request, response);
    }
}
