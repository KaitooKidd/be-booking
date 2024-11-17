package com.booking.users.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.booking.users.dtos.request.UserCreationRequest;
import com.booking.users.dtos.request.UserRequest;
import com.booking.users.dtos.response.UserResponse;
import com.booking.users.entity.UserEntity;

@Mapper(componentModel = "spring")
public interface UserMapper {
    UserEntity toUser(UserCreationRequest request);

    UserEntity toUserFromUserRequest(UserRequest request);

    @Mapping(target = "role", ignore = true)
    UserResponse toUserResponse(UserEntity user);
}
