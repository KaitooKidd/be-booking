package com.booking.customers.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.booking.auth.exception.AppException;
import com.booking.auth.exception.ErrorCode;
import com.booking.base.utils.StringUtils;
import com.booking.customers.dtos.request.CustomerRequest;
import com.booking.customers.dtos.response.CustomerResponse;
import com.booking.customers.entity.CustomerEntity;
import com.booking.customers.helper.CustomerHelper;
import com.booking.customers.mapper.CustomerMapper;
import com.booking.customers.repository.CustomerRepository;
import com.booking.customers.service.CustomerService;
import com.booking.users.constant.RoleConstant;
import com.booking.users.dtos.request.UserCreationRequest;
import com.booking.users.dtos.request.UserRequest;
import com.booking.users.entity.UserEntity;
import com.booking.users.service.UserService;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@RequiredArgsConstructor
@Service
@Log4j2
public class CustomerServiceImpl implements CustomerService {
    private final CustomerRepository customerRepository;
    private final UserService userService;
    private final CustomerMapper customerMapper;

    @Override
    public CustomerEntity save(CustomerEntity customer) {
        return customerRepository.save(customer);
    }

    @Override
    public CustomerEntity getCustomerByEmail(String email) {
        Optional<CustomerEntity> customer = customerRepository.findById(email);
        if (customer.isEmpty()) {
            throw new RuntimeException("Customer not found");
        }
        return customer.get();
    }

    @Override
    public CustomerResponse getCustomerByEmailWithFetch(String email) {
        CustomerEntity customer = customerRepository.findByEmail(email);
        if (customer == null) {
            throw new RuntimeException("Customer not found");
        }
        return CustomerHelper.toCustomerResponse(customer);
    }

    @Override
    public List<CustomerResponse> getAllCustomersByEmailWithFetch(List<String> emails) {
        List<CustomerEntity> customers = customerRepository.findAllByEmailsWithFetch(emails);
        return customers.stream().map(CustomerHelper::toCustomerResponse).toList();
    }

    @Override
    public List<CustomerResponse> getAllCustomersVerifiedWithFetch(Boolean isVerified) {
        List<CustomerEntity> customers = customerRepository.findAllByWithFetch();
        return customers.stream()
                .filter(c -> c.getUser().isVerified() == isVerified)
                .map(CustomerHelper::toCustomerResponse)
                .toList();
    }

    @Override
    public UserEntity createUnverifiedCustomer(CustomerRequest request, Boolean shouldCreateFirebaseUser) {
        UserEntity user = userService.createUser(UserCreationRequest.builder()
                .email(request.getEmail())
                .roleName(RoleConstant.CUSTOMER_ROLE)
                .isVerified(false)
                .shouldCreateFirebaseUser(shouldCreateFirebaseUser)
                .build());

        CustomerEntity customer = customerMapper.toCustomer(request);
        customer.setEmail(request.getEmail());
        customer.setName(
                StringUtils.isExist(request.getName())
                        ? request.getName()
                        : StringUtils.getEmailName(request.getEmail()));
        customer.setUser(user);

        save(customer);
        return user;
    }

    @Override
    public CustomerEntity updateCustomer(UserRequest userRequest, CustomerRequest request) {

        CustomerEntity customer = customerRepository.findByEmail(request.getEmail());

        if (customer == null) {
            log.error("Customer not found to update.");
            throw new AppException(ErrorCode.USER_EXISTED);
        }

        if (customer.getUser().getRole().getName().equals(RoleConstant.CUSTOMER_ROLE)
                && !userRequest.getEmail().equals(request.getEmail())) {
            log.error("Owner Customer can update info.");
            throw new AppException(ErrorCode.UNAUTHORIZED);
        }

        customerMapper.updateCustomer(customer, request);
        return save(customer);
    }

    @Override
    public void deleteCustomer(List<String> emails) {
        customerRepository.deleteAllByEmails(emails);
    }
}
