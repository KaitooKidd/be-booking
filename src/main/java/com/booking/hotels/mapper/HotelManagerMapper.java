package com.booking.hotels.mapper;

import org.mapstruct.Builder;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

import com.booking.hotels.dtos.request.HotelManagerRequest;
import com.booking.hotels.dtos.response.HotelManagerResponse;
import com.booking.hotels.entity.HotelManagerEntity;

@Mapper(componentModel = "spring", builder = @Builder(disableBuilder = true))
public interface HotelManagerMapper {
    HotelManagerResponse toHotelManagerResponse(HotelManagerEntity entity);

    void updateHotelManagerEntity(@MappingTarget HotelManagerEntity entity, HotelManagerRequest request);
}
