package com.booking.users.service;

import java.util.List;

import org.apache.coyote.BadRequestException;

import com.booking.users.dtos.request.UserCreationRequest;
import com.booking.users.dtos.request.UserRequest;
import com.booking.users.dtos.response.UserResponse;
import com.booking.users.entity.UserEntity;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseToken;

@SuppressWarnings("unused")
public interface UserService {
    UserEntity save(UserEntity user);

    FirebaseToken verifyToken(String tokenString) throws FirebaseAuthException;

    UserEntity getUserByEmail(String email, Boolean isVerified);

    UserEntity createUser(UserCreationRequest request);

    void createFirebaseUser(String email, String password);

    UserEntity verifyUser(String email) throws BadRequestException;

    void deleteFirebaseUser(String email);

    void deleteUser(String email);

    void deleteUsers(List<String> emails);

    UserResponse getUserInfo(UserRequest userRequest, Boolean isVerified);

    List<UserResponse> getListUserInfo();
}
