package com.booking.auth.controller;

import com.booking.auth.dto.request.VerifyEmailRequest;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.booking.auth.dto.response.ApiResponse;
import com.booking.auth.service.FirebaseAuthService;
import com.booking.users.dtos.request.UserRequest;
import com.booking.users.dtos.response.UserResponse;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@RestController
@RequestMapping("/auth")
@Tag(name = "Auth")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AuthenticationController {
    FirebaseAuthService firebaseAuthService;

    @PostMapping("/sign-in")
    UserResponse signIn(@AuthenticationPrincipal UserRequest userRequest) {
        return firebaseAuthService.signIn(userRequest);
    }

    @PostMapping("/sign-up")
    ApiResponse<Void> signUp(@AuthenticationPrincipal UserRequest userRequest) {
        return firebaseAuthService.signUp(userRequest);
    }

    @PostMapping("/verify-email")
    UserResponse verifyEmail(@RequestBody VerifyEmailRequest verifyEmailRequest) {
        return firebaseAuthService.verifyEmail(verifyEmailRequest.getEmail());
    }

    @PostMapping("/verify-email/resend")
    ApiResponse<Void> resendVerifyEmail(@AuthenticationPrincipal UserRequest userRequest) {
        String emailContent =
                firebaseAuthService.generateVerificationContent(userRequest).getContent();
        firebaseAuthService.sendVerificationEmail(userRequest.getEmail(), emailContent);
        return ApiResponse.<Void>builder().build();
    }
}
