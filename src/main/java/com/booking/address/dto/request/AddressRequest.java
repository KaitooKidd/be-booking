package com.booking.address.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AddressRequest {
    @NotBlank
    private String detail;
    @NotBlank
    private String ward;
    @NotBlank
    private String district;
    @NotBlank
    private String province;
    @NotBlank
    private String country;
}
