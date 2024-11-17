package com.booking.hotels.dtos.request;

import com.booking.address.dto.request.AddressRequest;
import com.booking.address.entity.AddressEntity;
import com.booking.base.entity.SequenceBaseEntity;
import com.booking.hotels.decorators.GalleryItemListConverter;
import com.booking.hotels.decorators.TimeRulesConverter;
import com.booking.hotels.dtos.GalleryItem;
import com.booking.hotels.dtos.HotelOverview;
import com.booking.hotels.dtos.TimeRules;
import com.booking.hotels.entity.HotelManagerEntity;
import com.booking.hotels.entity.RoomEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.*;
import org.hibernate.validator.constraints.URL;

import java.util.ArrayList;
import java.util.List;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class HotelRequest{
    @Email
    @NotBlank
    private String email;
    @NotBlank
    private String name;
    @NotBlank
    private String description;
    @URL
    private String imageUrl;
    private String imageKey;

    private List<GalleryItem> gallery = new ArrayList<>();
    private AddressRequest address;

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

    private TimeRules timeRules;


}
