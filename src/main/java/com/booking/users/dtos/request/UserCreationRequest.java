package com.booking.users.dtos.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserCreationRequest {
    @Email(message = "Email is invalid")
    @NotBlank(message = "Email is required")
    String email;

    @Size(min = 6, message = "Password is invalid")
    String password;

    @NotBlank(message = "Role is required")
    String roleName;

    Boolean isVerified = false;
    Boolean shouldCreateFirebaseUser = false;
}
