package com.booking.customers.controller;

import java.util.List;

import jakarta.validation.constraints.Email;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import com.booking.auth.dto.response.ApiResponse;
import com.booking.customers.dtos.request.CustomerRequest;
import com.booking.customers.dtos.response.CustomerResponse;
import com.booking.customers.helper.CustomerHelper;
import com.booking.customers.service.CustomerService;
import com.booking.users.dtos.request.UserRequest;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.log4j.Log4j2;

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
    CustomerResponse updateCustomer(
            @AuthenticationPrincipal UserRequest userRequest, @RequestBody CustomerRequest customerRequest) {
        return CustomerHelper.toCustomerResponse(customerService.updateCustomer(userRequest, customerRequest));
    }

    @GetMapping("/me")
    @PreAuthorize("hasAnyAuthority('admin','customer')")
    CustomerResponse getCurrentInfo(@AuthenticationPrincipal UserRequest userRequest) {
        return customerService.getCustomerByEmailWithFetch(userRequest.getEmail());
    }

    @GetMapping("")
    @PreAuthorize("hasAnyAuthority('admin')")
    List<CustomerResponse> listCustomers(@RequestParam(required = false) Boolean isVerified) {
        return customerService.getAllCustomersVerifiedWithFetch(isVerified);
    }

    @DeleteMapping("")
    @PreAuthorize("hasAnyAuthority('admin')")
    ApiResponse<Void> deleteCustomer(@RequestParam(required = false) List<@Email String> emails) {
        customerService.deleteCustomer(emails);
        return ApiResponse.<Void>builder().build();
    }
}
