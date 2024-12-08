package com.booking.customers.mapper;

import org.mapstruct.Builder;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

import com.booking.customers.dtos.request.CustomerRequest;
import com.booking.customers.entity.CustomerEntity;

@Mapper(componentModel = "spring", builder = @Builder(disableBuilder = true))
public interface CustomerMapper {
    CustomerEntity toCustomer(CustomerRequest request);

    void updateCustomer(@MappingTarget CustomerEntity entity, CustomerRequest request);
}
