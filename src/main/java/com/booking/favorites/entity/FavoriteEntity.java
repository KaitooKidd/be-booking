package com.booking.favorites.entity;

import jakarta.persistence.*;

import com.booking.base.entity.UUIDEntityWithoutTimestamp;
import com.booking.customers.entity.CustomerEntity;
import com.booking.hotels.entity.HotelEntity;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "favorites")
@SuppressWarnings("all")
public class FavoriteEntity extends UUIDEntityWithoutTimestamp {

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "hotel_id", referencedColumnName = "id", insertable = false, updatable = false)
    private HotelEntity hotel;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "customer_id", referencedColumnName = "id", insertable = false, updatable = false)
    private CustomerEntity customer;

    @Column(name = "hotel_id")
    private Long hotelId;

    @Column(name = "customer_id")
    private String customerEmail; // id = email
}
