package com.booking.hotels.dtos.request;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;

import org.hibernate.validator.constraints.URL;

import com.booking.hotels.dtos.GalleryItem;

import lombok.*;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@SuppressWarnings("all")
@Data
public class RoomRequest {
    @NotEmpty
    private String title;

    @NotEmpty
    private String description;

    @NotEmpty
    @URL
    private String imageUrl;

    @NotEmpty
    private String imageKey;

    private List<GalleryItem> gallery = new ArrayList<>();

    private int bedCount;
    private int guestCount;
    private int bathroomCount;
    private int kingBed;
    private int queenBed;

    private Double breakFastPrice;
    private Double roomPrice;

    private boolean roomService;
    private boolean tv;
    private boolean balcony;
    private boolean freeWifi;
    private boolean cityView;
    private boolean oceanView;
    private boolean forestView;
    private boolean mountainView;
    private boolean airCondition;
    private boolean soundProofed;
}
