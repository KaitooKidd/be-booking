package com.booking.customers.dtos.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.List;

@Data
public class DeleteCustomerRequest {
    @NotNull(message = "Email list must not be null")
    @NotEmpty(message = "Email list must not be empty")
    @Size(min = 1, message = "Email list must not be empty")
    private List<@Email String> emails;
}
