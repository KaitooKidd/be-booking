package com.booking.customers.mapper;

import com.booking.customers.dtos.request.CustomerRequest;
import com.booking.customers.dtos.response.CustomerResponse;
import com.booking.customers.entity.CustomerEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CustomerMapper {
    CustomerEntity toCustomer(CustomerRequest request);

}
