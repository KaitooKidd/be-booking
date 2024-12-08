package com.booking.hotels.dtos.response;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;

import com.booking.base.enums.GenderType;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class HotelManagerResponse {
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
}
