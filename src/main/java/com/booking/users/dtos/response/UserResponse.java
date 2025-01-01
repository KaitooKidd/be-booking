package com.booking.users.dtos.response;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserResponse {
    String id;
    String email;

    @JsonProperty("isVerified")
    Boolean verified;

    @JsonIgnore
    RoleResponse roleResponse;

    @JsonProperty("role")
    public String getRole() {
        return roleResponse != null ? roleResponse.getName() : null;
    }

    String name;
    String avatar;
}
