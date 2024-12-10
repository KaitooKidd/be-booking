package com.booking.bookings.service;

import com.booking.bookings.dtos.request.CreatePaymentUrlRequest;
import com.booking.bookings.dtos.response.PaymentUrlResponse;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;
import java.util.concurrent.CompletableFuture;

public interface PaymentService {
    PaymentUrlResponse createVnpayPaymentURL(HttpServletRequest req, CreatePaymentUrlRequest paymentUrlRequest) throws Exception;

    Map<String, String> vnpayPaymentResult(HttpServletRequest request) throws Exception;
}
