package com.booking.hotels.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TimeRules {
    private int timezone;
    private CheckinOut checkIn;
    private CheckinOut checkOut;

    @AllArgsConstructor
    @NoArgsConstructor
    @Data
    static class CheckinOut {
        private String start;
        private String end;
    }
}
