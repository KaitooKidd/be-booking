package com.booking.bookings.dtos.request;

import jakarta.persistence.Convert;
import jakarta.validation.constraints.Pattern;

import com.booking.bookings.enums.PaymentChannel;
import com.booking.bookings.enums.PaymentCurrency;
import com.booking.hotels.decorators.TimeRulesConverter;
import com.booking.hotels.dtos.TimeRules;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BookingRequest {
    private Long roomId;

    private Long hotelId;

    @Pattern(
            regexp = "^\\d{4}-(0[1-9]|1[0-2])-(0[1-9]|[12][0-9]|3[01])$",
            message = "Start Date must be in the format yyyy-mm-dd")
    private String startDate;

    @Pattern(
            regexp = "^\\d{4}-(0[1-9]|1[0-2])-(0[1-9]|[12][0-9]|3[01])$",
            message = "End Date must be in the format yyyy-mm-dd")
    private String endDate;

    private boolean breakfastIncluded;

    private PaymentCurrency currency = PaymentCurrency.VND;

    private Double totalPrice;

    @Convert(converter = TimeRulesConverter.class)
    private TimeRules timeRules;

    private PaymentChannel paymentChannel = PaymentChannel.vn_pay;
}
