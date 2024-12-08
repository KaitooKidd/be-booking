package com.booking.hotels.dtos.response;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import jakarta.persistence.Convert;

import com.booking.address.dto.response.AddressResponse;
import com.booking.hotels.decorators.GalleryItemListConverter;
import com.booking.hotels.decorators.TimeRulesConverter;
import com.booking.hotels.dtos.GalleryItem;
import com.booking.hotels.dtos.HotelOverview;
import com.booking.hotels.dtos.TimeRules;
import com.booking.reviews.dtos.response.ReviewResponse;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class HotelResponse {
    private Long id;
    private String email;

    private String name;
    private String description;
    private String imageUrl;
    private String imageKey;

    private AddressResponse address;

    @Convert(converter = GalleryItemListConverter.class)
    private List<GalleryItem> gallery = new ArrayList<>();

    private boolean gym;
    private boolean bar;
    private boolean restaurant;
    private boolean freeParking;
    private boolean movieNight;
    private boolean coffeeShop;
    private boolean spa;
    private boolean laundry;
    private boolean shopping;
    private boolean bikeRental;
    private boolean swimmingPool;
    private boolean allowPets;
    private boolean allowSmoking;

    @Convert(converter = TimeRulesConverter.class)
    private TimeRules timeRules;

    private HotelManagerResponse manager;
    private Date createdAt;
    private Date updatedAt;
    private Date deletedAt;

    private List<RoomResponse> rooms;
    private List<ReviewResponse> reviews;
    private HotelOverview overview;
}
