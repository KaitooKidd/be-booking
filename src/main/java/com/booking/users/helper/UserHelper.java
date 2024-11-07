package com.booking.users.helper;

import com.booking.base.utils.StringUtils;
import com.booking.users.constant.RoleConstant;
import com.booking.users.dtos.request.UserRequest;
import com.booking.users.dtos.response.UserResponse;
import com.booking.users.entity.UserEntity;
import com.booking.users.mapper.RoleMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Log4j2
public class UserHelper {
    private final RoleMapper roleMapper;

    public UserResponse transformUserResponse(UserEntity userEntity, UserRequest userRequest) {
        UserResponse userResponse = UserResponse.builder()
                .email(userEntity.getEmail())
                .isVerified(userEntity.isVerified())
                .role(roleMapper.toRoleResponse(userEntity.getRole())).build();

        if (userEntity.getRole().getName().equals(RoleConstant.ADMIN_ROLE)) {
            userResponse.setId(userEntity.getId().toString());
            if (userRequest != null) {
                userResponse.setName(StringUtils.isExist(userRequest.getName()) ? userRequest.getName() : StringUtils.getEmailName(userEntity.getEmail()));
                userResponse.setAvatar(userRequest.getPicture());
            }
            return userResponse;
        }
        if (userEntity.getRole().getName().equals(RoleConstant.CUSTOMER_ROLE)) {
            // TODO: 11/5/2024 Set Customer info
        }
        if (userEntity.getRole().getName().equals(RoleConstant.HOTEL_MANAGER_ROLE)) {
            // TODO: 11/5/2024 Set Hotel Manager info
        }
        if (userEntity.getRole().getName().equals(RoleConstant.RECEPTIONIST_ROLE)) {
            // TODO: 11/5/2024 Set Receptionist info
        }
        String message = "Role " + userEntity.getRole().getName() + " is not supported";
        log.error(message);
        throw new RuntimeException(message);
    }
}
