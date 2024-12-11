package com.booking.bookings.enums;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum BookingStatus {
    booked,
    checked_in,
    checked_out,
    reviewed;

    public static List<BookingStatus> nextValues(BookingStatus status, Boolean includeCurrent) {
        List<BookingStatus> all = new ArrayList<>(List.of(BookingStatus.values()));
        if (status == null) {
            return all;
        }

        int index = all.indexOf(status);
        if (index == -1) {
            throw new IllegalArgumentException();
        }
        return all.subList(index + (includeCurrent ? 1 : 0), all.size());
    }

    @JsonCreator
    public static BookingStatus fromValue(String value) {
        for (BookingStatus status : BookingStatus.values()) {
            if (status.name().equalsIgnoreCase(value)) {
                return status;
            }
        }
        throw new IllegalArgumentException("Invalid BookingStatus: " + value);
    }

    @JsonValue
    public String toValue() {
        return this.name().toLowerCase();
    }
}
