package com.booking.hotels.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

import com.booking.hotels.dtos.request.RoomRequest;
import com.booking.hotels.dtos.response.RoomResponse;
import com.booking.hotels.entity.RoomEntity;

@Mapper(componentModel = "spring")
public interface RoomMapper {
    RoomResponse toRoomResponse(RoomEntity roomEntity);

    void updateRoomEntity(@MappingTarget RoomEntity entity, RoomRequest request);
}
