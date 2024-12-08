package com.booking.customers.entity;

import java.util.List;

import jakarta.persistence.*;

import com.booking.address.entity.AddressEntity;
import com.booking.base.entity.UBaseEntity;
import com.booking.reviews.entity.ReviewEntity;
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
@Table(name = "customers")
@SuppressWarnings("all")
public class CustomerEntity extends UBaseEntity {

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private UserEntity user;

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @JoinColumn(name = "address_id")
    private AddressEntity address;

    //    @OneToMany(mappedBy = "customer", fetch = FetchType.LAZY)
    //    private List<BookingEntity> bookings;

    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<ReviewEntity> reviews;
}
