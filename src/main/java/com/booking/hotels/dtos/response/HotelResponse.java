package com.booking.hotels.dtos.response;

import com.booking.customers.entity.CustomerEntity;
import com.booking.users.entity.UserEntity;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class HotelResponse extends CustomerEntity {

    @JsonIgnore
    private UserEntity user;

    @JsonProperty("isVerified")
    public Boolean getIsVerified() {
        return user != null && user.isVerified();
    }

    private Date createdAt;

    @JsonProperty("gender")
    public String getGenderStringName() {
        return getGender().name;
    }
}
