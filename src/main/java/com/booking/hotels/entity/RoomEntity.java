package com.booking.hotels.entity;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.*;

import com.booking.base.entity.SequenceBaseEntity;
import com.booking.bookings.entity.BookingEntity;
import com.booking.hotels.decorators.GalleryItemListConverter;
import com.booking.hotels.dtos.GalleryItem;
import com.booking.reviews.entity.ReviewEntity;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "hotel_rooms")
@SuppressWarnings("all")
public class RoomEntity extends SequenceBaseEntity {
    private String title;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column(name = "image_url", columnDefinition = "TEXT")
    private String imageUrl;

    @Column(name = "image_key")
    private String imageKey;

    @Convert(converter = GalleryItemListConverter.class)
    private List<GalleryItem> gallery = new ArrayList<>();

    @Column(name = "bed_count")
    private int bedCount;

    @Column(name = "guest_count")
    private int guestCount;

    @Column(name = "bathroom_count")
    private int bathroomCount;

    @Column(name = "king_bed")
    private int kingBed;

    @Column(name = "queen_bed")
    private int queenBed;

    @Column(name = "breakfast_price")
    private Double breakFastPrice;

    @Column(name = "room_price")
    private Double roomPrice;

    @Column(name = "room_service")
    private boolean roomService;

    private boolean tv;
    private boolean balcony;

    @Column(name = "free_wifi")
    private boolean freeWifi;

    @Column(name = "city_view")
    private boolean cityView;

    @Column(name = "ocean_view")
    private boolean oceanView;

    @Column(name = "forest_view")
    private boolean forestView;

    @Column(name = "mountain_view")
    private boolean mountainView;

    @Column(name = "air_condition")
    private boolean airCondition;

    @Column(name = "sound_proofed")
    private boolean soundProofed;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "hotel_id", referencedColumnName = "id", insertable = false, updatable = false)
    private HotelEntity hotel;

    @Column(name = "hotel_id")
    private Long hotelId;

    @OneToMany(mappedBy = "room", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<ReviewEntity> reviews;

    @OneToMany(mappedBy = "room", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<BookingEntity> bookings;

    public List<String[]> getOccupiedTimes() {
        List<String[]> result = new ArrayList<>();
        if (bookings == null || bookings.isEmpty()) {
            return result;
        }

        LocalDate today = LocalDate.now();
        for (BookingEntity booking : bookings) {
            if (booking.getEndDate().isBefore(today)) {
                continue;
            }
            LocalDate startDate = booking.getStartDate().isBefore(today) ? today : booking.getStartDate();
            LocalDate endDate = booking.getEndDate();
            result.add(new String[] {startDate.toString(), endDate.toString()});
        }
        return result;
    }
}
