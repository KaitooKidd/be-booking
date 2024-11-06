package com.booking.users.mapper;

import com.booking.users.dtos.response.RoleResponse;
import com.booking.users.dtos.request.RoleRequest;
import com.booking.users.entity.RoleEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface RoleMapper {
    @Mapping(target = "permissions", ignore = true)
    RoleEntity toRole(RoleRequest request);

    RoleResponse toRoleResponse(RoleEntity roleEntity);
}
