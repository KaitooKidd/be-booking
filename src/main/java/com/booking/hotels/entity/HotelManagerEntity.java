package com.booking.hotels.entity;

import jakarta.persistence.*;

import com.booking.address.entity.AddressEntity;
import com.booking.base.entity.UBaseEntity;
import com.booking.users.entity.UserEntity;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "hotel_managers")
public class HotelManagerEntity extends UBaseEntity {

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private UserEntity user;

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @JoinColumn(name = "address_id")
    private AddressEntity address;

    @OneToOne(mappedBy = "manager", cascade = CascadeType.ALL, orphanRemoval = true)
    private HotelEntity hotel;
}
