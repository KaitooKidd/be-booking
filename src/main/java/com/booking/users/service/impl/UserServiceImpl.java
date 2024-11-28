package com.booking.users.service.impl;

import java.util.List;

import org.apache.coyote.BadRequestException;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import com.booking.auth.exception.AppException;
import com.booking.auth.exception.ErrorCode;
import com.booking.users.dtos.request.UserCreationRequest;
import com.booking.users.dtos.request.UserRequest;
import com.booking.users.dtos.response.UserResponse;
import com.booking.users.entity.RoleEntity;
import com.booking.users.entity.UserEntity;
import com.booking.users.helper.UserHelper;
import com.booking.users.mapper.UserMapper;
import com.booking.users.repository.UserRepository;
import com.booking.users.service.RoleService;
import com.booking.users.service.UserService;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseToken;
import com.google.firebase.auth.UserRecord;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.log4j.Log4j2;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Log4j2
@SuppressWarnings("all")
public class UserServiceImpl implements UserService {
    UserRepository userRepository;
    RoleService roleService;
    UserMapper userMapper;
    UserHelper userHelper;
    KafkaTemplate<String, Object> kafkaTemplate;

    @Override
    public UserEntity save(UserEntity user) {
        return userRepository.save(user);
    }

    @Override
    public FirebaseToken verifyToken(String tokenString) throws FirebaseAuthException {
        return FirebaseAuth.getInstance().verifyIdToken(tokenString);
    }

    @Override
    public UserEntity getUserByEmail(String email, Boolean isVerified) {
        if (isVerified != null)
            return userRepository.findByEmailAndVerified(email, isVerified).orElse(null);
        return userRepository.findByEmail(email).orElse(null);
    }

    @Override
    public UserEntity createUser(UserCreationRequest request) {
        UserEntity existingUser = getUserByEmail(request.getEmail(), null);
        if (existingUser != null) {
            throw new AppException(ErrorCode.USER_EXISTED);
        }
        RoleEntity role = roleService.getRoleByName(request.getRoleName());

        UserEntity user = save(UserEntity.builder()
                .email(request.getEmail())
                .verified(request.getIsVerified())
                .role(role)
                .build());

        if (request.getShouldCreateFirebaseUser()) {
            createFirebaseUser(request.getEmail(), request.getPassword());
        }
        return user;
    }

    @Override
    public void createFirebaseUser(String email, String password) {
        try {
            FirebaseAuth.getInstance()
                    .createUser(new UserRecord.CreateRequest().setEmail(email).setPassword(password));
            log.info("Create user {} successfully", email);
        } catch (FirebaseAuthException e) {
            String message = "Create firebase user error: " + e.getMessage();
            log.error(message);
            throw new RuntimeException(message);
        }
    }

    @Override
    public UserResponse verifyUser(String email) throws BadRequestException {
        UserEntity userEntity = getUserByEmail(email, null);
        if (userEntity == null) {
            throw new RuntimeException("Unverified user " + email + " was not created before verifying");
        }
        if (userEntity.isVerified()) {
            throw new BadRequestException("User " + email + " has already been verified");
        }

        userEntity.setVerified(true);
        return userMapper.toUserResponse(save(userEntity));
    }

    @Override
    public void deleteFirebaseUser(String email) {
        try {
            UserRecord userRecord = FirebaseAuth.getInstance().getUserByEmail(email);
            FirebaseAuth.getInstance().deleteUser(userRecord.getUid());
            log.info("Delete user {} successfully", email);
        } catch (FirebaseAuthException e) {
            String message = "Delete firebase user error: " + e.getMessage();
            log.error(message);
            throw new RuntimeException(message);
        }
    }

    @Override
    public void deleteUser(String email) {
        userRepository.deleteByEmail(email);
        deleteFirebaseUser(email);
    }

    @Override
    public void deleteUsers(List<String> emails) {
        emails.forEach(this::deleteUser);
    }

    @Override
    public UserResponse getUserInfo(UserRequest userRequest, Boolean isVerified) {
        UserEntity userEntity = getUserByEmail(userRequest.getEmail(), isVerified);

        if (userEntity == null) {
            String message = "User " + userRequest.getEmail() + " not found";
            log.error(message);
            throw new RuntimeException(message);
        }
        return userHelper.transformUserResponse(userEntity, userRequest);
    }

    @Override
    public List<UserResponse> getListUserInfo() {
        List<UserEntity> userEntities = userRepository.findAll();
        return userEntities.stream()
                .map(userEntity -> userHelper.transformUserResponse(userEntity, null))
                .toList();
    }

    //    @PreAuthorize("hasRole('ADMIN')")
    //    public UserResponse getUser(String id) {
    //        return userMapper.toUserResponse(
    //                userRepository.findById(id).orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED)));
    //    }
}
