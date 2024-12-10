package com.booking.bookings.entity;

import com.booking.base.entity.UUIDEntity;
import com.booking.bookings.decorators.PaymentInfoConverter;
import com.booking.bookings.dtos.PaymentInfo;
import com.booking.bookings.enums.BookingStatus;
import com.booking.bookings.enums.PaymentChannel;
import com.booking.bookings.enums.PaymentCurrency;
import com.booking.customers.entity.CustomerEntity;
import com.booking.hotels.decorators.TimeRulesConverter;
import com.booking.hotels.dtos.TimeRules;
import com.booking.hotels.entity.HotelEntity;
import com.booking.hotels.entity.RoomEntity;
import com.booking.reviews.entity.ReviewEntity;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "bookings")
@SuppressWarnings("all")
public class BookingEntity extends UUIDEntity {

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "hotel_id", referencedColumnName = "id", insertable = false, updatable = false)
    private HotelEntity hotel;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "room_id", referencedColumnName = "id", insertable = false, updatable = false)
    private RoomEntity room;

    @OneToOne(mappedBy = "booking", fetch = FetchType.LAZY)
    private ReviewEntity review;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "customer_id", referencedColumnName = "id", insertable = false, updatable = false)
    private CustomerEntity customer;

    @Column(name = "customer_name", nullable = false)
    private String customerName;

    @Column(name = "hotel_owner_email", nullable = false)
    private String hotelOwnerEmail;

    @Column(name = "start_date")
    private LocalDate startDate;

    @Column(name = "end_date")
    private LocalDate endDate;

    @Convert(converter = TimeRulesConverter.class)
    private TimeRules timeRules;

    @Column(name = "breakfast_included")
    private boolean breakfastIncluded;

    @Column(name = "currency")
    @Enumerated(EnumType.STRING)
    private PaymentCurrency currency;

    @Column(name = "total_price")
    private Double totalPrice;

    @Column(name = "is_paid")
    private boolean isPaid;

    @Column(name = "payment_channel")
    @Enumerated(EnumType.STRING)
    private PaymentChannel paymentChannel;

    @Column(name = "payment_id", unique = true)
    private String paymentId;

    @Column(name = "payment_info")
    @Convert(converter = PaymentInfoConverter.class)
    private transient PaymentInfo paymentInfo;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private BookingStatus status;

    @Column(name = "created_at", updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Date createdAt;

    @Column(name = "room_id")
    private Long roomId;

    @Column(name = "hotel_id")
    private Long hotelId;

    @Column(name = "customer_id")
    private String customerEmail; // id = email

}
