package com.booking.auth.service;

import com.booking.auth.dto.VerificationContent;
import com.booking.auth.dto.response.ApiResponse;
import com.booking.users.dtos.request.UserRequest;
import com.booking.users.dtos.response.UserResponse;
import com.booking.users.entity.UserEntity;

@SuppressWarnings("unused")
public interface FirebaseAuthService {
    UserRequest authenticate(String authToken);

    UserResponse signIn(UserRequest userRequest);

    ApiResponse<Void> signUp(UserRequest userRequest);

    UserResponse verifyEmail(String token);

    VerificationContent generateVerificationContent(UserRequest userRequest);

    String getVerificationContent(VerificationContent.VerificationLinks links);

    void sendVerificationEmail(String email, String content);
}
