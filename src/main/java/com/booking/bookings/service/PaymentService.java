package com.booking.bookings.service;

import java.util.Map;

import jakarta.servlet.http.HttpServletRequest;

import com.booking.bookings.dtos.request.CreatePaymentUrlRequest;
import com.booking.bookings.dtos.response.PaymentUrlResponse;

public interface PaymentService {
    PaymentUrlResponse createVnpayPaymentURL(HttpServletRequest req, CreatePaymentUrlRequest paymentUrlRequest)
            throws Exception;

    Map<String, String> vnpayPaymentResult(HttpServletRequest request) throws Exception;
}
