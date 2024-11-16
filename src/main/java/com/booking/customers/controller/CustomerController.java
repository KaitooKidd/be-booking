package com.booking.customers.controller;

import com.booking.auth.dto.response.ApiResponse;
import com.booking.customers.dtos.request.CustomerRequest;
import com.booking.customers.dtos.request.DeleteCustomerRequest;
import com.booking.customers.dtos.request.GetListCustomerRequest;
import com.booking.customers.dtos.response.CustomerResponse;
import com.booking.customers.entity.CustomerEntity;
import com.booking.customers.service.CustomerService;
import com.booking.users.dtos.request.UserRequest;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/customers")
@Tag(name = "Customers")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Log4j2
public class CustomerController {
    CustomerService customerService;

    @PatchMapping("")
    @PreAuthorize("hasAnyAuthority('admin','customer')")
    CustomerEntity updateCustomer(@AuthenticationPrincipal UserRequest userRequest, @RequestBody CustomerRequest customerRequest) {
        return customerService.updateCustomer(userRequest, customerRequest);
    }

    @GetMapping("/me")
    @PreAuthorize("hasAnyAuthority('admin','customer')")
    CustomerResponse getCurrentInfo(@AuthenticationPrincipal UserRequest userRequest) {
        return customerService.getCustomerByEmailWithFetch(userRequest.getEmail());
    }

    @GetMapping("")
    @PreAuthorize("hasAnyAuthority('admin')")
    List<CustomerResponse> listCustomers(@RequestBody GetListCustomerRequest listCustomerRequest) {
        return customerService.getAllCustomersVerifiedWithFetch(listCustomerRequest.isVerified());
    }

    @DeleteMapping("")
    @PreAuthorize("hasAnyAuthority('admin')")
    ApiResponse<Void> deleteCustomer(@RequestBody DeleteCustomerRequest deleteCustomerRequest) {
        customerService.deleteCustomer(deleteCustomerRequest.getEmails());
        return ApiResponse.<Void>builder()
                .build();
    }
}
