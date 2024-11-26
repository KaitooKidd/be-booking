package com.booking.hotels.mapper;

import com.booking.hotels.dtos.request.HotelManagerRequest;
import com.booking.hotels.dtos.request.HotelRequest;
import com.booking.hotels.dtos.response.HotelManagerResponse;
import com.booking.hotels.dtos.response.HotelResponse;
import com.booking.hotels.entity.HotelEntity;
import com.booking.hotels.entity.HotelManagerEntity;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface HotelManagerMapper {
    HotelManagerResponse toHotelManagerResponse(HotelManagerEntity entity);
    void updateHotelManagerEntity(@MappingTarget HotelManagerEntity entity, HotelManagerRequest request);
}
