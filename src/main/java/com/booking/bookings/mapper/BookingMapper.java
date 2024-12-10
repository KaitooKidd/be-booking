package com.booking.bookings.mapper;

import com.booking.bookings.dtos.request.BookingRequest;
import com.booking.bookings.dtos.response.BookingResponse;
import com.booking.bookings.entity.BookingEntity;
import org.mapstruct.Builder;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring", builder = @Builder(disableBuilder = true))
public interface BookingMapper {
    BookingEntity toEntity(BookingRequest request);

    BookingResponse toResponse(BookingEntity entity);

    void updateEntity(@MappingTarget BookingEntity entity, BookingRequest request);
}
