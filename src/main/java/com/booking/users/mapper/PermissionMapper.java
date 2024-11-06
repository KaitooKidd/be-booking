package com.booking.users.mapper;

import com.booking.users.entity.PermissionEntity;
import org.mapstruct.Mapper;

import com.booking.users.dtos.request.PermissionRequest;
import com.booking.users.dtos.response.PermissionResponse;


@Mapper(componentModel = "spring")
public interface PermissionMapper {
    PermissionEntity toPermission(PermissionRequest request);

    PermissionResponse toPermissionResponse(PermissionEntity permission);
}
