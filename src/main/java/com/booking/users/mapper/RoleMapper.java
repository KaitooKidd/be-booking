package com.booking.users.mapper;

import org.mapstruct.Builder;
import org.mapstruct.Mapper;

import com.booking.users.dtos.request.RoleRequest;
import com.booking.users.dtos.response.RoleResponse;
import com.booking.users.entity.RoleEntity;

@Mapper(componentModel = "spring", builder = @Builder(disableBuilder = true))
public interface RoleMapper {
    RoleEntity toRole(RoleRequest request);

    RoleResponse toRoleResponse(RoleEntity roleEntity);
}
