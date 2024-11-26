package com.booking.hotels.service;

import com.booking.hotels.dtos.request.RoomRequest;
import com.booking.hotels.dtos.response.RoomResponse;
import com.booking.hotels.entity.RoomEntity;
import com.booking.users.dtos.request.UserRequest;

import java.util.List;

@SuppressWarnings("unused")
public interface RoomService {
    RoomEntity getById(Long id);

    RoomEntity createRoom(Long hotelId, UserRequest userRequest, RoomRequest request);

    RoomEntity updateRoom(Long roomId, RoomRequest request);

    void deleteRoom(Long roomId);

    List<RoomResponse> listHotelRooms(Long hotelId);
}
