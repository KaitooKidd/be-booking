package com.booking.customers.dtos.response;

import java.util.Date;

import com.booking.address.dto.response.AddressResponse;
import com.booking.address.entity.AddressEntity;
import com.booking.base.enums.GenderType;
import com.booking.customers.entity.CustomerEntity;
import com.booking.users.dtos.response.UserResponse;
import com.booking.users.entity.UserEntity;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
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
