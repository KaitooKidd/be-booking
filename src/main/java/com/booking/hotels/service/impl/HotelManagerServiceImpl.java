package com.booking.hotels.service.impl;

import org.springframework.stereotype.Service;

import com.booking.auth.exception.AppException;
import com.booking.auth.exception.ErrorCode;
import com.booking.hotels.dtos.request.HotelManagerRequest;
import com.booking.hotels.dtos.response.HotelManagerResponse;
import com.booking.hotels.entity.HotelManagerEntity;
import com.booking.hotels.mapper.HotelManagerMapper;
import com.booking.hotels.repository.HotelManagerRepository;
import com.booking.hotels.service.HotelManagerService;
import com.booking.users.constant.RoleConstant;
import com.booking.users.dtos.request.UserRequest;
import com.booking.users.dtos.response.UserResponse;
import com.booking.users.service.UserService;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@RequiredArgsConstructor
@Log4j2
@Service
public class HotelManagerServiceImpl implements HotelManagerService {
    private final HotelManagerRepository hotelManagerRepository;
    private final HotelManagerMapper hotelManagerMapper;
    private final UserService userService;

    @Override
    public HotelManagerResponse getHotelManagerByEmail(String email) {
        HotelManagerEntity hotelManager = hotelManagerRepository.findByEmail(email);
        if (hotelManager == null) {
            throw new AppException("Hotel manager not found", ErrorCode.USER_NOT_EXISTED);
        }
        return hotelManagerMapper.toHotelManagerResponse(hotelManager);
    }

    @Override
    public HotelManagerResponse updateHotelManager(UserRequest userRequest, HotelManagerRequest request) {
        HotelManagerEntity hotelManager =
                hotelManagerRepository.findById(request.getEmail()).orElse(null);
        if (hotelManager == null) {
            throw new AppException("Update fail: Hotel manager not found", ErrorCode.USER_NOT_EXISTED);
        }

        UserResponse userResponse = userService.getUserInfo(userRequest, null);
        if (userResponse.getRole().equals(RoleConstant.HOTEL_MANAGER_ROLE)
                && !userRequest.getEmail().equals(request.getEmail())) {
            log.error("Owner Hotel Manager can update info.");
            throw new AppException(ErrorCode.UNAUTHORIZED);
        }

        hotelManagerMapper.updateHotelManagerEntity(hotelManager, request);
        return hotelManagerMapper.toHotelManagerResponse(hotelManagerRepository.save(hotelManager));
    }
}
