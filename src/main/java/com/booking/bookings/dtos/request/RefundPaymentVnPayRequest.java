package com.booking.bookings.dtos.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@Builder
@SuppressWarnings("unused")
@NoArgsConstructor
public class RefundPaymentVnPayRequest {
    private String bookingId;
}
