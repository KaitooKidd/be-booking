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
import com.booking.receptionists.entity.ReceptionistEntity;
import com.booking.reviews.entity.ReviewEntity;

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
    @JoinColumn(name = "manager_email", referencedColumnName = "id", insertable = false, updatable = false)
    private HotelManagerEntity manager;

    @Column(name = "manager_email", unique = true)
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

    @OneToMany(mappedBy = "hotel", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<ReceptionistEntity> receptionists;

    @OneToMany(mappedBy = "hotel", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<ReviewEntity> reviews;

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
    public HotelOverview getOverview() {
        Double minPrice = null;
        HotelOverview hotelOverview = new HotelOverview();
        hotelOverview.setRooms(0, 0.0);
        hotelOverview.setReviews(0, 0);
        if (rooms != null && rooms.size() > 0) {
            minPrice = Double.MAX_VALUE;
            for (RoomEntity room : rooms) {
                if (room.getRoomPrice() < minPrice) {
                    minPrice = room.getRoomPrice();
                }
            }
            hotelOverview.setRooms(rooms.size(), minPrice);
        }

        if (reviews != null && reviews.size() > 0) {
            int total = 0;
            int average = 0;
            for (ReviewEntity review : reviews) {
                total += review.getTotal();
            }
            average = total / reviews.size();
            hotelOverview.setReviews(reviews.size(), average);
        }
        return hotelOverview;
    }
}
