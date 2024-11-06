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
    @Email(message = "INVALID_EMAIL")
    @NotBlank(message = "EMAIL_IS_REQUIRED")
    String email;

    @Size(min = 6, message = "INVALID_PASSWORD")
    String password;

    @NotBlank(message = "ROLE_IS_REQUIRED")
    String roleName;

    Boolean isVerified = false;
    Boolean shouldCreateFirebaseUser = false;
}
