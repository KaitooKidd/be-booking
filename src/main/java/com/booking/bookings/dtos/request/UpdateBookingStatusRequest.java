package com.booking.bookings.dtos.request;

import com.booking.bookings.enums.BookingStatus;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UpdateBookingStatusRequest {
    private BookingStatus status;

    public UpdateBookingStatusRequest() {}

    public BookingStatus getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = BookingStatus.fromValue(status);
    }
}
