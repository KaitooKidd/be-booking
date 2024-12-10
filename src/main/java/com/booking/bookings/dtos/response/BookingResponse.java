package com.booking.bookings.dtos.response;

import com.booking.bookings.dtos.PaymentInfo;
import com.booking.bookings.enums.BookingStatus;
import com.booking.bookings.enums.PaymentChannel;
import com.booking.bookings.enums.PaymentCurrency;
import com.booking.customers.dtos.response.CustomerResponse;
import com.booking.hotels.dtos.TimeRules;
import com.booking.hotels.dtos.response.HotelResponse;
import com.booking.hotels.dtos.response.RoomResponse;
import com.booking.reviews.dtos.response.ReviewResponse;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;
import java.util.Date;

@Data
@SuperBuilder
@NoArgsConstructor
public class BookingResponse {
    private String id;
    private HotelResponse hotel;

    private RoomResponse room;

    private ReviewResponse review;

    private CustomerResponse customer;

    private String customerName;

    private String hotelOwnerEmail;

    private LocalDate startDate;

    private LocalDate endDate;

    private TimeRules timeRules;

    private boolean breakfastIncluded;

    private PaymentCurrency currency;

    private Double totalPrice;

    private boolean isPaid;

    private PaymentChannel paymentChannel;

    private String paymentId;

    private transient PaymentInfo paymentInfo;

    private BookingStatus status;

    private Date createdAt;

    private Long roomId;

    private Long hotelId;

    private String customerEmail;
}
