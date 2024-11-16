package com.booking.customers.helper;

import com.booking.customers.dtos.response.CustomerResponse;
import com.booking.customers.entity.CustomerEntity;
import org.modelmapper.ModelMapper;

public class CustomerHelper {
    public static CustomerResponse toCustomerResponse(CustomerEntity customer) {
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(customer, CustomerResponse.class);
    }
}
