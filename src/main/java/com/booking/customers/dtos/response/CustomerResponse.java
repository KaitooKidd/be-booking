package com.booking.customers.dtos.response;

import java.util.Date;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;

import com.booking.address.dto.response.AddressResponse;
import com.booking.base.enums.GenderType;
import com.booking.users.dtos.response.UserResponse;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CustomerResponse {

    @JsonProperty("id")
    private String email; // email as the ID

    private String name;
    private String avatarKey;
    private String avatar;
    private String birthday;
    private String phone;

    private AddressResponse address;
    @Enumerated(EnumType.STRING)
    @JsonIgnore
    private GenderType gender;
    @JsonProperty("gender")
    public String getGenderStringName() {
        return getGender() == null ? "" : getGender().name;
    }

    @JsonIgnore
    private UserResponse user;
    @JsonProperty("isVerified")
    public Boolean getIsVerified() {
        return user != null && user.getVerified();
    }

    private Date createdAt;
}
