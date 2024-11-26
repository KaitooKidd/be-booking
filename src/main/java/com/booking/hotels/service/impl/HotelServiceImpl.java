package com.booking.hotels.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.booking.auth.exception.AppException;
import com.booking.auth.exception.ErrorCode;
import com.booking.base.utils.StringUtils;
import com.booking.hotels.dtos.request.HotelRequest;
import com.booking.hotels.dtos.response.HotelResponse;
import com.booking.hotels.entity.HotelEntity;
import com.booking.hotels.entity.HotelManagerEntity;
import com.booking.hotels.mapper.HotelMapper;
import com.booking.hotels.repository.HotelRepository;
import com.booking.hotels.service.HotelService;
import com.booking.users.constant.RoleConstant;
import com.booking.users.dtos.request.UserCreationRequest;
import com.booking.users.dtos.request.UserRequest;
import com.booking.users.entity.UserEntity;
import com.booking.users.service.UserService;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@RequiredArgsConstructor
@Log4j2
@Service
public class HotelServiceImpl implements HotelService {
    private final HotelRepository hotelRepository;
    private final UserService userService;
    private final HotelMapper hotelMapper;

    @Override
    public List<HotelResponse> findAll() {
        List<HotelEntity> all = hotelRepository.findAll();
        return all.stream().map(hotelMapper::toHotelResponse).toList();
    }

    @Override
    public HotelResponse createHotel(HotelRequest request) {
        HotelEntity newHotelEntity = new HotelEntity();
        hotelMapper.updateHotelEntity(newHotelEntity, request);
        UserEntity user = userService.createUser(UserCreationRequest.builder()
                .email(request.getEmail())
                .roleName(RoleConstant.HOTEL_MANAGER_ROLE)
                .isVerified(true)
                .shouldCreateFirebaseUser(true)
                .build());

        HotelManagerEntity hotelManagerEntity = new HotelManagerEntity();
        hotelManagerEntity.setEmail(request.getEmail());
        hotelManagerEntity.setName(StringUtils.getEmailName(request.getEmail()));
        hotelManagerEntity.setUser(user);

        newHotelEntity.setManager(hotelManagerEntity);
        return hotelMapper.toHotelResponse(hotelRepository.save(newHotelEntity));
    }

    @Override
    public HotelResponse getMyHotel(UserRequest userRequest) {
        UserEntity user = userService.getUserByEmail(userRequest.getEmail(), null);
        if (user.getRole().getName().equals(RoleConstant.HOTEL_MANAGER_ROLE)) {
            return hotelMapper.toHotelResponse(getHotelByEmail(user.getEmail()));
        }
        if (user.getRole().getName().equals(RoleConstant.RECEPTIONIST_ROLE)) {
            return hotelMapper.toHotelResponse(getReceptionistHotel(user.getEmail()));
        }

        throw new AppException(ErrorCode.FORBIDDEN_REQUEST);
    }

    @Override
    public HotelEntity getReceptionistHotel(String receptionistEmail) {
        // TODO: 11/21/2024 Find by receptionist email
        return null;
    }

    @Override
    public HotelEntity getHotelById(Long id) {
        HotelEntity hotelEntity = hotelRepository.findById(id).orElse(null);
        if (hotelEntity == null) {
            throw new AppException("Hotel not found", ErrorCode.USER_NOT_EXISTED);
        }
        return hotelEntity;
    }

    @Override
    public HotelEntity getHotelByEmail(String email) {
        HotelEntity hotelEntity = hotelRepository.findByEmail(email);
        if (hotelEntity == null) {
            throw new AppException("Hotel not found", ErrorCode.USER_NOT_EXISTED);
        }
        return hotelEntity;
    }

    @Override
    public HotelResponse updateHotel(Long id, UserRequest userRequest, HotelRequest request) {
        HotelEntity currentHotel = getHotelById(id);

        UserEntity user = userService.getUserByEmail(userRequest.getEmail(), null);
        if (user.getRole().getName().equals(RoleConstant.HOTEL_MANAGER_ROLE)
                && (!userRequest.getEmail().equals(request.getEmail())
                        || !userRequest.getEmail().equals(currentHotel.getEmail()))) {
            log.error("Owner Hotel can update info.");
            throw new AppException(ErrorCode.UNAUTHORIZED);
        }
        hotelMapper.updateHotelEntity(currentHotel, request);
        return hotelMapper.toHotelResponse(hotelRepository.save(currentHotel));
    }
}
