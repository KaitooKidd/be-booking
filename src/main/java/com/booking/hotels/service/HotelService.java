package com.booking.hotels.service;

import com.booking.hotels.dtos.request.HotelRequest;
import com.booking.hotels.dtos.response.HotelResponse;
import com.booking.hotels.entity.HotelEntity;
import com.booking.users.dtos.request.UserRequest;

import java.util.List;

@SuppressWarnings("unused")
public interface HotelService {
    List<HotelResponse> findAll();

    HotelResponse createHotel(HotelRequest request);

    HotelResponse getMyHotel(UserRequest userRequest);

    HotelEntity getReceptionistHotel(String receptionistEmail);

    HotelEntity getHotelById(Long id);

    HotelEntity getHotelByEmail(String email);

    HotelResponse updateHotel(Long id, UserRequest userRequest, HotelRequest request);
}
