package com.booking.base.entity;

import jakarta.persistence.*;

import lombok.Getter;
import lombok.Setter;

@MappedSuperclass
@Getter
@Setter
public abstract class SequenceBaseEntity extends TimestampEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // use type Long for ID
}
