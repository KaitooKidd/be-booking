package com.booking.address.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;

import com.booking.base.entity.SequenceBaseEntity;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@SuppressWarnings("ALL")
@Entity
@Table(name = "addresses")
public class AddressEntity extends SequenceBaseEntity {
    private String details;
    private String ward;
    private String district;
    private String province;
    private String country;
}
