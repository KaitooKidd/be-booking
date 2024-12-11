package com.booking.bookings.controller;

import java.util.List;
import java.util.Map;

import jakarta.servlet.http.HttpServletRequest;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

import com.booking.auth.dto.response.ApiResponse;
import com.booking.bookings.dtos.request.BookingRequest;
import com.booking.bookings.dtos.request.CreatePaymentUrlRequest;
import com.booking.bookings.dtos.request.UpdateBookingStatusRequest;
import com.booking.bookings.dtos.response.BookingResponse;
import com.booking.bookings.dtos.response.PaymentUrlResponse;
import com.booking.bookings.service.BookingService;
import com.booking.bookings.service.PaymentService;
import com.booking.users.dtos.request.UserRequest;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.log4j.Log4j2;

@RestController
@RequestMapping("/bookings")
@Tag(name = "Bookings")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Log4j2
public class BookingController {
    BookingService bookingService;
    PaymentService paymentService;

    @PatchMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('receptionist','hotel_manager')")
    ApiResponse<Void> updateBookingStatus(
            @PathVariable("id") String id,
            @AuthenticationPrincipal UserRequest userRequest,
            @RequestBody UpdateBookingStatusRequest updateRequest) {
        bookingService.updateBookingStatus(id, updateRequest, userRequest);
        return ApiResponse.<Void>builder().code(1000).message("Success").build();
    }

    @PostMapping("")
    @PreAuthorize("hasAnyAuthority('customer')")
    BookingResponse createBooking(
            @AuthenticationPrincipal UserRequest userRequest, @RequestBody BookingRequest bookingRequest) {
        return bookingService.createBooking(userRequest, bookingRequest);
    }

    @GetMapping("")
    List<BookingResponse> listMyBookings(@AuthenticationPrincipal UserRequest userRequest) {
        return bookingService.listMyBookings(userRequest);
    }

    @PostMapping("/payment/vnpay")
    //    @PreAuthorize("hasAnyAuthority('customer')")
    PaymentUrlResponse createVnpayPaymentURL(
            HttpServletRequest request, @RequestBody CreatePaymentUrlRequest bookingRequest) throws Exception {
        return paymentService.createVnpayPaymentURL(request, bookingRequest);
    }

    @GetMapping("/payment/return/vnpay")
    RedirectView vnpayPaymentResult(HttpServletRequest request) throws Exception {
        Map<String, String> result = paymentService.vnpayPaymentResult(request);
        String redirectUrl = result.get("url");
        return new RedirectView(redirectUrl);
    }
}
