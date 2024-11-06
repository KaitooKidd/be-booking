package com.booking.auth.service.impl;

import com.booking.auth.constant.LocaleConstant;
import com.booking.auth.constant.RequestConstant;
import com.booking.auth.dto.VerificationContent;
import com.booking.auth.dto.response.ApiResponse;
import com.booking.auth.exception.AuthorizationException;
import com.booking.auth.service.FirebaseAuthService;
import com.booking.auth.service.MailService;
import com.booking.auth.utils.JwtUtils;
import com.booking.base.utils.StringUtils;
import com.booking.users.dtos.request.UserRequest;
import com.booking.users.entity.UserEntity;
import com.booking.users.service.UserService;
import com.google.firebase.auth.FirebaseToken;
import io.jsonwebtoken.JwtException;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Log4j2
public class FirebaseAuthServiceImpl implements FirebaseAuthService {
    private final UserService userService;
    private final MailService mailService;

    @Value("${email.client-url}")
    private String clientUrl;

    @Override
    public UserRequest authenticate(String authToken) {
        if (!StringUtils.isExist(authToken)) {
            String message = "Missing Bearer Token";
            log.error(message);
            throw new AuthorizationException("Missing Bearer Token");
        }

        if (!authToken.startsWith(RequestConstant.AUTH_TOKEN_PREFIX)) {
            String message = "Invalid Bearer Token";
            log.error(message);
            throw new AuthorizationException(message);
        }

        String tokenString = authToken.substring(RequestConstant.AUTH_TOKEN_PREFIX.length());
        try {
            FirebaseToken decodedToken = userService.verifyToken(tokenString);
            if (decodedToken.getEmail() == null) {
                String message = "Token does not contain the user email";
                log.error(message);
                throw new AuthorizationException(message);
            }
            UserRequest userRequest = new UserRequest();
            userRequest.setEmail(decodedToken.getEmail());
            userRequest.setPicture(decodedToken.getPicture());
            userRequest.setName(decodedToken.getName());
            return userRequest;
        } catch (Exception e) {
            throw new AuthorizationException(e.getMessage());
        }
    }

    @Override
    public UserEntity signIn(UserRequest userRequest) {
        UserEntity user = userService.getUserByEmail(userRequest.getEmail(), null);
        if (user == null) {
            String message = "User " + userRequest.getEmail() + " is not found";
            log.error(message);
            throw new AuthorizationException(message);
        }
        return user;
    }

    @Override
    public ApiResponse<Void> signUp(UserRequest userRequest) {
        UserEntity user = userService.getUserByEmail(userRequest.getEmail(), null);
        if (user == null) {
            String message = "User " + userRequest.getEmail() + " is not found";
            log.error(message);
            throw new AuthorizationException(message);
        }

        // TODO: 11/5/2024 Create customer no verify

        String emailContent = generateVerificationContent(userRequest).getContent();
        sendVerificationEmail(userRequest.getEmail(), emailContent);
        return ApiResponse.<Void>builder()
                .code(200)
                .message("Verification email sent").build();
    }

    @Override
    public UserEntity verifyEmail(String token) throws BadRequestException {
        String username;
        try {
            username = JwtUtils.extractUsername(token);
        } catch (JwtException e) {
            log.error(e);
            throw new AuthorizationException(e.getMessage());
        }

        return userService.verifyUser(username);
    }

    @Override
    public VerificationContent generateVerificationContent(UserRequest userRequest) {
        String token = JwtUtils.generateToken(userRequest);
        String enLink = String.format("%s/%s/verify-email?token=%s", clientUrl, LocaleConstant.EN, token);
        String vnLink = String.format("%s/%s/verify-email?token=%s", clientUrl, LocaleConstant.VN, token);
        VerificationContent.VerificationLinks links = new VerificationContent.VerificationLinks(vnLink, enLink);

        return VerificationContent.builder()
                .token(token)
                .link(links)
                .content(getVerificationContent(links))
                .build();
    }
    @Override
    public String getVerificationContent(VerificationContent.VerificationLinks links) {
        // TODO: 11/5/2024 Create content mail to verify
        return "";
    }

    @Override
    public void sendVerificationEmail(String email, String content) {
        log.info("Send verification email");
        mailService.sendVerificationEmail(email, content);
        log.info("Send verification email: Success");
    }
}
