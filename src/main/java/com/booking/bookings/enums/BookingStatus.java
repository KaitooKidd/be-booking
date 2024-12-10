package com.booking.bookings.enums;

import java.util.ArrayList;
import java.util.List;

public enum BookingStatus {
    booked, checked_in, checked_out, reviewed;

    public static List<BookingStatus> nextValues(BookingStatus status, Boolean includeCurrent) {
        List<BookingStatus> all = new ArrayList<>(List.of(BookingStatus.values()));
        if (status == null) {
            return  all;
        }

        int index = all.indexOf(status);
        if (index == -1) {
            throw new IllegalArgumentException();
        }
        return all.subList(index + (includeCurrent ? 1 : 0), all.size());
    }
}
