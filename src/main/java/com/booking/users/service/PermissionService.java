package com.booking.users.service;

import com.booking.users.dtos.request.PermissionRequest;
import com.booking.users.dtos.response.PermissionResponse;

import java.util.List;

public interface PermissionService {
    PermissionResponse create(PermissionRequest request);

    List<PermissionResponse> getAll();

    void delete(String permission);
}
