package com.booking.hotels.mapper;

import com.booking.hotels.dtos.request.HotelRequest;
import com.booking.hotels.dtos.response.HotelResponse;
import com.booking.hotels.entity.HotelEntity;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface HotelMapper {
    HotelResponse toHotelResponse(HotelEntity hotelEntity);
    void updateHotelEntity(@MappingTarget HotelEntity entity, HotelRequest request);
}
