package com.booking.receptionists.entity;

import com.booking.hotels.entity.HotelEntity;
import jakarta.persistence.*;

import com.booking.address.entity.AddressEntity;
import com.booking.base.entity.UBaseEntity;
import com.booking.users.entity.UserEntity;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
@Table(name = "receptionists")
@SuppressWarnings("all")
public class ReceptionistEntity extends UBaseEntity {

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private UserEntity user;

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @JoinColumn(name = "address_id")
    private AddressEntity address;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "hotel_id", referencedColumnName = "id", insertable = false, updatable = false)
    private HotelEntity hotel;

    @Column(name = "hotel_id")
    private Long hotelId;
}
