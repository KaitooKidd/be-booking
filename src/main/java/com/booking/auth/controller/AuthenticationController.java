package com.booking.auth.controller;

import java.text.ParseException;

import com.booking.auth.dto.request.*;
import com.booking.auth.dto.response.AuthenticationResponse;
import com.booking.auth.dto.response.IntrospectResponse;
import com.booking.auth.service.FirebaseAuthService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nimbusds.jose.JOSEException;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AuthenticationController {
    FirebaseAuthService authenticationService;

//    @PostMapping("/token")
//    ApiResponse<AuthenticationResponse> authenticate(@RequestBody AuthenticationRequest request) {
//        var result = authenticationService.authenticate(request);
//        return ApiResponse.<AuthenticationResponse>builder().result(result).build();
//    }
//
//    @PostMapping("/introspect")
//    ApiResponse<IntrospectResponse> authenticate(@RequestBody IntrospectRequest request) {
//        var result = authenticationService.introspect(request);
//        return ApiResponse.<IntrospectResponse>builder().result(result).build();
//    }
//
//    @PostMapping("/refresh")
//    ApiResponse<AuthenticationResponse> authenticate(@RequestBody RefreshRequest request)
//            throws ParseException, JOSEException {
//        var result = authenticationService.refreshToken(request);
//        return ApiResponse.<AuthenticationResponse>builder().result(result).build();
//    }
//
//    @PostMapping("/logout")
//    ApiResponse<Void> logout(@RequestBody LogoutRequest request) throws ParseException, JOSEException {
//        authenticationService.logout(request);
//        return ApiResponse.<Void>builder().build();
//    }
}
