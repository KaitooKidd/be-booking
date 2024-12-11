package com.booking.bookings.dtos.request;

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
public class CreatePaymentUrlRequest {
    private VNPayBankCode bankCode;
    private String bookingId;

    @Builder.Default
    private String locale = LocaleConstant.VN;

    public CreatePaymentUrlRequest(VNPayBankCode bankCode, String bookingId) {
        this.bankCode = bankCode;
        this.bookingId = bookingId;
    }
}
