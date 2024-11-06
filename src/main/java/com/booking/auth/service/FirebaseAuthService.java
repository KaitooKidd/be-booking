package com.booking.auth.service;

import com.booking.auth.dto.VerificationContent;
import com.booking.auth.dto.response.ApiResponse;
import com.booking.users.dtos.request.UserRequest;
import com.booking.users.entity.UserEntity;
import org.apache.coyote.BadRequestException;

@SuppressWarnings("unused")
public interface FirebaseAuthService {
    UserRequest authenticate(String authToken);

    UserEntity signIn(UserRequest userRequest);

    ApiResponse<Void> signUp(UserRequest userRequest);

    UserEntity verifyEmail(String token) throws BadRequestException;

    VerificationContent generateVerificationContent(UserRequest userRequest);

    String getVerificationContent(VerificationContent.VerificationLinks links);

    void sendVerificationEmail(String email, String content);
}
