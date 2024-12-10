package com.booking.bookings.dtos.request;

import com.booking.bookings.enums.BookingStatus;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UpdateBookingStatusRequest {
    private BookingStatus status;
}
