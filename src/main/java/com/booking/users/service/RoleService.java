package com.booking.users.service;

import java.util.List;

import com.booking.users.dtos.request.RoleRequest;
import com.booking.users.dtos.response.RoleResponse;
import com.booking.users.entity.RoleEntity;

public interface RoleService {
    RoleEntity save(RoleEntity roleEntity);

    RoleResponse create(RoleRequest request);

    List<RoleResponse> getAll();

    void deleteByRole(String role);

    RoleEntity getRoleByName(String roleName);

    void delete(Long id);
}
