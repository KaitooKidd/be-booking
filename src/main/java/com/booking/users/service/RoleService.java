package com.booking.users.service;

import com.booking.users.dtos.request.RoleRequest;
import com.booking.users.dtos.response.RoleResponse;
import com.booking.users.entity.RoleEntity;

import java.util.List;

public interface RoleService {
    RoleResponse create(RoleRequest request);

    List<RoleResponse> getAll();

    void deleteByRole(String role);

    RoleEntity getRoleByName(String roleName);

    void delete(Long id);
}
