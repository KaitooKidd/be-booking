package com.booking.hotels.entity;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.*;

import com.booking.address.entity.AddressEntity;
import com.booking.base.entity.SequenceBaseEntity;
import com.booking.hotels.decorators.GalleryItemListConverter;
import com.booking.hotels.decorators.TimeRulesConverter;
import com.booking.hotels.dtos.GalleryItem;
import com.booking.hotels.dtos.HotelOverview;
import com.booking.hotels.dtos.TimeRules;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "hotels")
@SuppressWarnings("all")
public class HotelEntity extends SequenceBaseEntity {

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @JoinColumn(name = "manager_email")
    private HotelManagerEntity manager;

    @Column(name = "email", unique = true)
    private String email;

    private String name;
    private String description;

    @Column(name = "image_url")
    private String imageUrl;

    @Column(name = "image_key")
    private String imageKey;

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @JoinColumn(name = "address_id")
    private AddressEntity address;

    @OneToMany(mappedBy = "hotel", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<RoomEntity> rooms;

    @Convert(converter = GalleryItemListConverter.class)
    private List<GalleryItem> gallery = new ArrayList<>();

    private boolean gym;
    private boolean bar;
    private boolean restaurant;

    @Column(name = "free_parking")
    private boolean freeParking;

    @Column(name = "movie_night")
    private boolean movieNight;

    @Column(name = "coffee_shop")
    private boolean coffeeShop;

    private boolean spa;
    private boolean laundry;
    private boolean shopping;

    @Column(name = "bike_rental")
    private boolean bikeRental;

    @Column(name = "swimming_pool")
    private boolean swimmingPool;

    @Column(name = "allow_pets")
    private boolean allowPets;

    @Column(name = "allow_smoking")
    private boolean allowSmoking;

    @Convert(converter = TimeRulesConverter.class)
    private TimeRules timeRules;

    //    @OneToMany(() => BookingEntity, (booking) => booking.hotel)
    //    bookings: BookingEntity[];
    //
    //    @Expose({ groups: ['reviews'] })
    //    @OneToMany(() => ReviewEntity, (review) => review.hotel)
    //    reviews: ReviewEntity[];
    //
    //    @OneToMany(() => ReceptionistEntity, (receptionist) => receptionist.hotel)
    //    receptionists: ReceptionistEntity[];
    public HotelOverview getOverview() {
        Double minPrice = null;
        if (rooms != null) {
            minPrice = Double.MAX_VALUE;
            for (RoomEntity room : rooms) {
                if (room.getRoomPrice() < minPrice) {
                    minPrice = room.getRoomPrice();
                }
            }
        }
        HotelOverview hotelOverview = new HotelOverview();
        hotelOverview.setRooms(rooms.size(), minPrice);

        // TODO: 11/16/2024 Set review to HotelOverview
        return hotelOverview;
    }
}
