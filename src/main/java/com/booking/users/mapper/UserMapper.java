package com.booking.users.mapper;

import com.booking.users.dtos.request.UserCreationRequest;
import com.booking.users.dtos.request.UserRequest;
import com.booking.users.dtos.response.UserResponse;
import com.booking.users.entity.UserEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {
    UserEntity toUser(UserCreationRequest request);

    UserEntity toUserFromUserRequest(UserRequest request);
    UserResponse toUserResponse(UserEntity user);

}
