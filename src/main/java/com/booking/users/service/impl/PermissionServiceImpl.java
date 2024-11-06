package com.booking.users.service.impl;

import java.util.List;

import com.booking.users.entity.PermissionEntity;
import com.booking.users.mapper.PermissionMapper;
import com.booking.users.repository.PermissionRepository;
import org.springframework.stereotype.Service;

import com.booking.users.dtos.request.PermissionRequest;
import com.booking.users.dtos.response.PermissionResponse;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class PermissionServiceImpl implements com.booking.users.service.PermissionService {
    PermissionRepository permissionRepository;
    PermissionMapper permissionMapper;

    @Override
    public PermissionResponse create(PermissionRequest request) {
        PermissionEntity permission = permissionMapper.toPermission(request);
        permission = permissionRepository.save(permission);
        return permissionMapper.toPermissionResponse(permission);
    }

    @Override
    public List<PermissionResponse> getAll() {
        var permissions = permissionRepository.findAll();
        return permissions.stream().map(permissionMapper::toPermissionResponse).toList();
    }

    @Override
    public void delete(String permission) {
        permissionRepository.deleteById(permission);
    }
}
