package com.booking.hotels.entity;

import com.booking.address.entity.AddressEntity;
import com.booking.base.entity.SequenceBaseEntity;
import com.booking.base.entity.TimestampEntity;
import com.booking.base.entity.UBaseEntity;
import com.booking.hotels.decorators.GalleryItemListConverter;
import com.booking.hotels.dtos.GalleryItem;
import com.booking.users.entity.UserEntity;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

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

    @OneToOne(cascade = CascadeType.ALL,  orphanRemoval = true, fetch = FetchType.LAZY)
    @JoinColumn(name = "address_id")
    private AddressEntity address;

    @OneToOne(mappedBy = "manager", cascade = CascadeType.ALL, orphanRemoval = true)
    private HotelEntity hotel;
}
