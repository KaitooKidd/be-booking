package com.booking.reviews.entity;

import com.booking.bookings.entity.BookingEntity;
import jakarta.persistence.*;

import com.booking.base.entity.UUIDEntity;
import com.booking.customers.entity.CustomerEntity;
import com.booking.hotels.entity.HotelEntity;
import com.booking.hotels.entity.RoomEntity;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "reviews")
@SuppressWarnings("all")
public class ReviewEntity extends UUIDEntity {

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "hotel_id", referencedColumnName = "id", insertable = false, updatable = false)
    private HotelEntity hotel;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "room_id", referencedColumnName = "id", insertable = false, updatable = false)
    private RoomEntity room;

    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "booking_id", referencedColumnName = "id", insertable = false, updatable = false)
    private BookingEntity booking;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "customer_id", referencedColumnName = "id", insertable = false, updatable = false)
    private CustomerEntity customer;

    @Column(name = "customer_name", nullable = false)
    private String customerName;

    @Column(name = "customer_image")
    private String customerImage;

    @Column(name = "hotel_owner_email", nullable = false)
    private String hotelOwnerEmail;

    // Rating info
    @Column(name = "staff_rating")
    private Double staffRating;

    @Column(name = "facility_rating")
    private Double facilityRating;

    @Column(name = "cleanliness_rating")
    private Double cleanlinessRating;

    @Column(name = "comfort_rating")
    private Double comfortRating;

    @Column(name = "value_for_money_rating")
    private Double valueForMoneyRating;

    @Column(name = "location_rating")
    private Double locationRating;

    @Column(name = "comment")
    private String comment;

    @Column(name = "booking_id")
    private String bookingId;

    @Column(name = "room_id")
    private Long roomId;

    @Column(name = "hotel_id")
    private Long hotelId;

    @Column(name = "customer_id")
    private String customerEmail; // id = email

    public Double getTotal() {
        return Math.round((staffRating
                + facilityRating
                + cleanlinessRating
                + comfortRating
                + valueForMoneyRating
                + locationRating)
                * 100.0
                / 6.0)
                / 100.0;
    }
}
