package com.booking.bookings.service;

import java.util.List;

import com.booking.bookings.dtos.PaymentInfo;
import com.booking.bookings.dtos.request.BookingRequest;
import com.booking.bookings.dtos.request.UpdateBookingStatusRequest;
import com.booking.bookings.dtos.response.BookingResponse;
import com.booking.bookings.entity.BookingEntity;
import com.booking.users.dtos.request.UserRequest;

public interface BookingService {
    BookingEntity getBookingById(String id, Boolean isPaid);

    BookingEntity getBookingByPaymentId(String paymentId);

    BookingEntity save(BookingEntity reviewEntity);

    void updateBookingStatus(String bookingId, UpdateBookingStatusRequest request, UserRequest userRequest);

    BookingEntity updateBookingPaymentValue(String paymentId, Boolean isPaid, PaymentInfo paymentInfo);

    BookingResponse createBooking(UserRequest userRequest, BookingRequest request);

    List<BookingResponse> listMyBookings(UserRequest userRequest);
}
