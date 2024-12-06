package com.booking.hotels.service;

import com.booking.hotels.dtos.request.HotelManagerRequest;
import com.booking.hotels.dtos.response.HotelManagerResponse;
import com.booking.users.dtos.request.UserRequest;

@SuppressWarnings("unused")
public interface HotelManagerService {
    HotelManagerResponse getHotelManagerByEmail(String email);

    HotelManagerResponse updateHotelManager(UserRequest userRequest, HotelManagerRequest request);
}
