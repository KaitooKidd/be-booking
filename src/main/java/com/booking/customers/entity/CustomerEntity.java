package com.booking.customers.entity;

import jakarta.persistence.*;

import com.booking.address.entity.AddressEntity;
import com.booking.base.entity.TimestampEntity;
import com.booking.customers.enums.GenderType;
import com.booking.users.entity.UserEntity;
import com.fasterxml.jackson.annotation.JsonProperty;

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
public class CustomerEntity extends TimestampEntity {
    @Id
    @Column(name = "id", nullable = false, unique = true)
    @JsonProperty("id")
    private String email; // email as the ID

    private String name;
    private String avatarKey;
    private String avatar;
    private String birthday;
    private String phone;

    @Enumerated(EnumType.STRING)
    private GenderType gender;

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
