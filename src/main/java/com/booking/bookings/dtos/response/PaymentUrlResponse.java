package com.booking.bookings.dtos.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@Builder
@SuppressWarnings("unused")
@NoArgsConstructor
public class PaymentUrlResponse {
    private String status = "success";
    private String data;
}
