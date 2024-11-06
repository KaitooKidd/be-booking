package com.booking.users.controller;

import java.util.List;

import com.booking.users.service.RoleService;
import org.springframework.web.bind.annotation.*;

import com.booking.auth.dto.response.ApiResponse;
import com.booking.users.dtos.request.RoleRequest;
import com.booking.users.dtos.response.RoleResponse;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/roles")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class RoleController {
    RoleService roleService;

    @PostMapping
    ApiResponse<RoleResponse> create(@RequestBody RoleRequest request) {
        return ApiResponse.<RoleResponse>builder()
                .result(roleService.create(request))
                .build();
    }

    @GetMapping
    ApiResponse<List<RoleResponse>> getAll() {
        return ApiResponse.<List<RoleResponse>>builder()
                .result(roleService.getAll())
                .build();
    }

    @DeleteMapping("/{role}")
    ApiResponse<Void> delete(@PathVariable String role) {
        roleService.deleteByRole(role);
        return ApiResponse.<Void>builder().build();
    }
}
