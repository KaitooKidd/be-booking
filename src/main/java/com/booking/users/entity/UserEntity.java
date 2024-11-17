package com.booking.users.entity;

import jakarta.persistence.*;

import com.booking.base.entity.TimestampEntity;
import com.booking.customers.entity.CustomerEntity;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
@Table(name = "users")
public class UserEntity extends TimestampEntity {
    @Id
    @Column(nullable = false, unique = true)
    private String email; // email as the ID

    @Column(name = "verified", nullable = false)
    @Builder.Default
    private boolean verified = false;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "role_id", nullable = false)
    @JsonManagedReference
    private RoleEntity role;

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private CustomerEntity customer;
    //
    //    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    //    private HotelManagerEntity hotelManager;
    //
    //    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    //    private ReceptionistEntity receptionist;
}
