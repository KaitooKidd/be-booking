package com.booking.customers.service;

import java.util.List;

import com.booking.customers.dtos.request.CustomerRequest;
import com.booking.customers.dtos.response.CustomerResponse;
import com.booking.customers.entity.CustomerEntity;
import com.booking.users.dtos.request.UserRequest;
import com.booking.users.entity.UserEntity;

public interface CustomerService {
    CustomerEntity save(CustomerEntity customer);

    CustomerEntity getCustomerByEmail(String email);

    CustomerResponse getCustomerByEmailWithFetch(String email);

    List<CustomerResponse> getAllCustomersByEmailWithFetch(List<String> emails);

    List<CustomerResponse> getAllCustomersVerifiedWithFetch(Boolean isVerified);

    UserEntity createUnverifiedCustomer(CustomerRequest request, Boolean shouldCreateFirebaseUser);

    CustomerEntity updateCustomer(UserRequest userRequest, CustomerRequest request);

    void deleteCustomer(List<String> emails);
}
