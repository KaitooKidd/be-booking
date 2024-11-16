package com.booking.users.controller;

import com.booking.auth.dto.response.ApiResponse;
import com.booking.users.dtos.request.UserCreationRequest;
import com.booking.users.dtos.response.UserResponse;
import com.booking.users.service.UserService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.log4j.Log4j2;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
@Tag(name = "Users")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Log4j2
public class UserController {
    UserService userService;

    @PostMapping("/firebase")
    ApiResponse<Void> createFirebaseAccount(@RequestBody UserCreationRequest request) {
        userService.createFirebaseUser(request.getEmail(), "123456");
        return ApiResponse.<Void>builder()
                .message("Create firebase account success")
                .build();
    }

    @GetMapping("")
    @PreAuthorize("hasAuthority('admin')")
    ApiResponse<List<UserResponse>> listUsers() {
        return ApiResponse.<List<UserResponse>>builder()
                .result(userService.getListUserInfo())
                .build();
    }
}
