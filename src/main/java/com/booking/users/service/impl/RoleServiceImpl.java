package com.booking.users.service.impl;

import java.util.HashSet;
import java.util.List;

import com.booking.users.dtos.request.RoleRequest;
import com.booking.users.dtos.response.RoleResponse;
import com.booking.users.entity.RoleEntity;
import com.booking.users.repository.PermissionRepository;
import com.booking.users.repository.RoleRepository;
import com.booking.users.service.RoleService;
import org.springframework.stereotype.Service;

import com.booking.users.mapper.RoleMapper;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class RoleServiceImpl implements RoleService {
    RoleRepository roleRepository;
    PermissionRepository permissionRepository;
    RoleMapper roleMapper;

    @Override
    public RoleResponse create(RoleRequest request) {
        var role = roleMapper.toRole(request);

        var permissions = permissionRepository.findAllById(request.getPermissions());
        role.setPermissions(new HashSet<>(permissions));

        role = roleRepository.save(role);
        return roleMapper.toRoleResponse(role);
    }

    @Override
    public List<RoleResponse> getAll() {
        return roleRepository.findAll().stream().map(roleMapper::toRoleResponse).toList();
    }

    @Override
    public void deleteByRole(String role) {
        roleRepository.deleteByName(role);
    }

    @Override
    public RoleEntity getRoleByName(String roleName) {
        return roleRepository.findByName(roleName);
    }

    @Override
    public void delete(Long id) {
        roleRepository.deleteById(id);
    }
}
