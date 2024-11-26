package com.booking.hotels.dtos.response;

import com.booking.hotels.decorators.GalleryItemListConverter;
import com.booking.hotels.dtos.GalleryItem;
import jakarta.persistence.Convert;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RoomResponse {
    private String title;
    private String description;
    private String imageUrl;
    private String imageKey;

    @Convert(converter = GalleryItemListConverter.class)
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
