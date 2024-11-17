package com.booking.customers.entity;

import com.booking.address.entity.AddressEntity;
import com.booking.base.entity.TimestampEntity;
import com.booking.base.entity.UBaseEntity;
import com.booking.customers.enums.GenderType;
import com.booking.users.entity.UserEntity;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
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

    //    @OneToMany(mappedBy = "customer", fetch = FetchType.LAZY)
    //    private List<ReviewEntity> reviews;
}
