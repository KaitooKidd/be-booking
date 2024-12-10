package com.booking.reviews.dtos.response;

import com.booking.customers.entity.CustomerEntity;
import com.booking.hotels.dtos.response.HotelResponse;
import com.booking.hotels.dtos.response.RoomResponse;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@NoArgsConstructor
public class ReviewResponse {
    private String id;
    private HotelResponse hotel;

    private RoomResponse room;
    //    private BookingEntity booking;

    private CustomerEntity customer;
    private String customerName;
    private String customerImage;
    private String hotelOwnerEmail;

    private Double staffRating;
    private Double facilityRating;
    private Double cleanlinessRating;
    private Double comfortRating;
    private Double valueForMoneyRating;
    private Double locationRating;
    private String comment;

    private Double total;
}
