package com.booking.users.dtos.response;

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

    String role;
    String name;
    String avatar;
}
