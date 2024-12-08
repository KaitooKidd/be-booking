package com.booking.receptionists.dtos.response;

import com.booking.address.dto.response.AddressResponse;
import com.booking.base.enums.GenderType;
import com.booking.hotels.dtos.response.HotelResponse;
import com.booking.users.dtos.response.UserResponse;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
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
public class ReceptionistResponse {
    @JsonProperty("id")
    private String email; // email as the ID

    private String name;
    private String avatarKey;
    private String avatar;
    private String birthday;
    private String phone;

    @Enumerated(EnumType.STRING)
    @JsonIgnore
    private GenderType gender;

    @JsonProperty("gender")
    public String getGenderStringName() {
        return getGender() == null ? "" : getGender().name;
    }

    @JsonIgnore
    private UserResponse user;
    private AddressResponse address;

    @JsonProperty("isVerified")
    public Boolean getIsVerified() {
        return user != null && user.getIsVerified();
    }

    private Date createdAt;


}
