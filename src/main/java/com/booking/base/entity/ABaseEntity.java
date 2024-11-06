package com.booking.base.entity;

import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@MappedSuperclass
@Getter
@Setter
public abstract class ABaseEntity extends TimestampEntity {
    @Id
    private UUID id; // Assume that we're using UUIDs for IDs
}
