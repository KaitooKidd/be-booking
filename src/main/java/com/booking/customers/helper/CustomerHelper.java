package com.booking.customers.helper;

import org.modelmapper.ModelMapper;

import com.booking.customers.dtos.response.CustomerResponse;
import com.booking.customers.entity.CustomerEntity;

public class CustomerHelper {
    public static CustomerResponse toCustomerResponse(CustomerEntity customer) {
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(customer, CustomerResponse.class);
    }
}
