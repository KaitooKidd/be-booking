package com.booking.hotels.mapper;

import com.booking.customers.dtos.request.CustomerRequest;
import com.booking.customers.entity.CustomerEntity;
import com.booking.hotels.entity.HotelEntity;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface HotelMapper {
    HotelEntity toHotelEntity(CustomerRequest request);

    void updateCustomer(@MappingTarget HotelEntity entity, CustomerRequest request);
}
