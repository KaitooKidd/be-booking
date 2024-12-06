package com.booking.address.dto.response;

import java.util.Date;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AddressResponse {
    private String details;
    private String ward;
    private String district;
    private String province;
    private String country;
    private Date createdAt;
    private Date updatedAt;
    private Date deletedAt;
}
