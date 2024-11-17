package com.booking.users.service.impl;

import com.booking.users.dtos.request.RoleRequest;
import com.booking.users.dtos.response.RoleResponse;
import com.booking.users.entity.RoleEntity;
import com.booking.users.mapper.RoleMapper;
import com.booking.users.repository.RoleRepository;
import com.booking.users.service.RoleService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Log4j2
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class RoleServiceImpl implements RoleService {
    RoleRepository roleRepository;
    RoleMapper roleMapper;
    @Override
    public RoleEntity save(RoleEntity roleEntity) {
        return roleRepository.save(roleEntity);
    }
    @Override
    public RoleResponse create(RoleRequest request) {
        var role = roleMapper.toRole(request);

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
