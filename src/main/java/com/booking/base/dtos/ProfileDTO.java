package com.booking.base.dtos;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

import com.booking.address.dto.request.AddressRequest;
import com.booking.customers.enums.GenderType;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class ProfileDTO {
    @Email(message = "Email is invalid")
    @NotBlank(message = "Email is required")
    String email;

    private String name;
    private String avatar;
    private String phone;
    private String avatarKey;

    @Pattern(
            regexp = "^\\d{4}-(0[1-9]|1[0-2])-(0[1-9]|[12][0-9]|3[01])$",
            message = "The birthday must be in the format yyyy-mm-dd")
    private String birthday;

    @Enumerated(EnumType.STRING)
    private GenderType gender;

    @Valid
    private AddressRequest address;

    public void setGender(String gender) {
        this.gender = GenderType.valueOf(gender.toUpperCase());
    }
}
