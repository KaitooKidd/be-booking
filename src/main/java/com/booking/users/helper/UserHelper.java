package com.booking.users.helper;

import org.springframework.stereotype.Component;

import com.booking.base.utils.StringUtils;
import com.booking.users.constant.RoleConstant;
import com.booking.users.dtos.request.UserRequest;
import com.booking.users.dtos.response.UserResponse;
import com.booking.users.entity.UserEntity;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Component
@RequiredArgsConstructor
@Log4j2
public class UserHelper {

    public UserResponse transformUserResponse(UserEntity userEntity, UserRequest userRequest) {
        UserResponse userResponse = UserResponse.builder()
                .email(userEntity.getEmail())
                .isVerified(userEntity.isVerified())
                .role(userEntity.getRole().getName())
                .build();

        if (userEntity.getRole().getName().equals(RoleConstant.ADMIN_ROLE)) {
            userResponse.setId(userEntity.getEmail());
            if (userRequest != null) {
                userResponse.setName(
                        StringUtils.isExist(userRequest.getName())
                                ? userRequest.getName()
                                : StringUtils.getEmailName(userEntity.getEmail()));
                userResponse.setAvatar(userRequest.getPicture());
            }
            return userResponse;
        }
        if (userEntity.getRole().getName().equals(RoleConstant.CUSTOMER_ROLE)) {
            userResponse.setId(userEntity.getCustomer().getEmail());
            userResponse.setName(userEntity.getCustomer().getName());
            userResponse.setAvatar(userEntity.getCustomer().getAvatar());
            return userResponse;
        }
        if (userEntity.getRole().getName().equals(RoleConstant.HOTEL_MANAGER_ROLE)) {
            userResponse.setId(userEntity.getHotelManager().getEmail());
            userResponse.setName(userEntity.getHotelManager().getName());
            userResponse.setAvatar(userEntity.getHotelManager().getAvatar());
            return userResponse;
        }
        if (userEntity.getRole().getName().equals(RoleConstant.RECEPTIONIST_ROLE)) {
            userResponse.setId(userEntity.getReceptionist().getEmail());
            userResponse.setName(userEntity.getReceptionist().getName());
            userResponse.setAvatar(userEntity.getReceptionist().getAvatar());
            return userResponse;
        }
        String message = "Role " + userEntity.getRole().getName() + " is not supported";
        log.error(message);
        throw new RuntimeException(message);
    }
}
