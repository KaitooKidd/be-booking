package com.booking.auth.configuration;

import com.booking.auth.filter.FirebaseRequestFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private static final String[] PUBLIC_ENDPOINTS = {
        "/users/registration", "/auth/token", "/auth/introspect", "/auth/logout", "/auth/refresh"
    };
    private final FirebaseRequestFilter firebaseRequestFilter;
    private final CustomAuthenticationEntryPoint authEntryPoint;


    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http
                .cors(AbstractHttpConfigurer::disable)
                .csrf(AbstractHttpConfigurer::disable)
                .addFilterAfter(firebaseRequestFilter, BasicAuthenticationFilter.class)
                .authorizeHttpRequests(
                        authorizeRequests -> authorizeRequests
                                .anyRequest().authenticated()
                )
                .exceptionHandling(exceptionHandler -> exceptionHandler.authenticationEntryPoint(authEntryPoint).accessDeniedHandler(authEntryPoint));

        return http.build();
    }
}
