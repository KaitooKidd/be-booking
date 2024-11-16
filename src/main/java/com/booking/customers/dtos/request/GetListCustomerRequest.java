package com.booking.customers.dtos.request;

import com.booking.base.utils.StringUtils;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class GetListCustomerRequest {
    @JsonProperty("isVerified")
    private String isVerified;

    public Boolean isVerified() {
        return StringUtils.isExist(isVerified) && isVerified.equalsIgnoreCase("true") ;
    }
}
