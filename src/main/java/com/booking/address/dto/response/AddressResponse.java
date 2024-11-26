package com.booking.address.dto.response;

import lombok.Builder;
import lombok.Data;

import java.util.Date;

@Data
@Builder
public class AddressResponse {
    private String detail;
    private String ward;
    private String district;
    private String province;
    private String country;
    private Date createdAt;
    private Date updatedAt;
    private Date deletedAt;
}
