package com.booking.hotels.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.booking.auth.exception.AppException;
import com.booking.auth.exception.ErrorCode;
import com.booking.hotels.dtos.request.RoomRequest;
import com.booking.hotels.dtos.response.RoomResponse;
import com.booking.hotels.entity.HotelEntity;
import com.booking.hotels.entity.RoomEntity;
import com.booking.hotels.mapper.RoomMapper;
import com.booking.hotels.repository.RoomRepository;
import com.booking.hotels.service.HotelService;
import com.booking.hotels.service.RoomService;
import com.booking.users.constant.RoleConstant;
import com.booking.users.dtos.request.UserRequest;
import com.booking.users.entity.UserEntity;
import com.booking.users.service.UserService;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@RequiredArgsConstructor
@Log4j2
@Service
public class RoomServiceImpl implements RoomService {
    private final HotelService hotelService;
    private final UserService userService;
    private final RoomRepository roomRepository;
    private final RoomMapper roomMapper;

    @Override
    public RoomEntity getById(Long id) {
        Optional<RoomEntity> roomEntity = roomRepository.findById(id);
        if (roomEntity.isEmpty()) {
            throw new AppException("Room not found", ErrorCode.USER_NOT_EXISTED);
        }
        return roomEntity.get();
    }

    @Override
    public RoomEntity createRoom(Long hotelId, UserRequest userRequest, RoomRequest request) {
        HotelEntity hotel = hotelService.getHotelById(hotelId);
        UserEntity user = userService.getUserByEmail(userRequest.getEmail(), null);

        if (user.getRole().getName().equals(RoleConstant.HOTEL_MANAGER_ROLE)
                && !userRequest.getEmail().equals(hotel.getEmail())) {
            log.error("Owner Hotel can update info.");
            throw new AppException(ErrorCode.UNAUTHORIZED);
        }

        RoomEntity roomEntity = new RoomEntity();
        roomMapper.updateRoomEntity(roomEntity, request);
        roomEntity.setHotelId(hotelId);
        return roomRepository.save(roomEntity);
    }

    @Override
    public RoomEntity updateRoom(Long roomId, RoomRequest request) {
        RoomEntity roomEntity = getById(roomId);
        roomMapper.updateRoomEntity(roomEntity, request);
        return roomRepository.save(roomEntity);
    }

    @Override
    public void deleteRoom(Long roomId) {
        RoomEntity room = getById(roomId);
        roomRepository.deleteById(roomId);
    }

    @Override
    public List<RoomResponse> listHotelRooms(Long hotelId) {
        List<RoomEntity> roomEntities = roomRepository.findListHotelRooms(hotelId);
        return roomEntities.stream().map(roomMapper::toRoomResponse).toList();
    }
}
