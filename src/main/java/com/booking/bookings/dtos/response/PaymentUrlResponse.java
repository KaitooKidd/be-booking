package com.booking.bookings.dtos.response;

import com.booking.auth.constant.LocaleConstant;
import com.booking.bookings.enums.VNPayBankCode;
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
