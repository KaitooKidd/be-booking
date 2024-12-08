package com.booking.base.entity;

import java.util.UUID;

import jakarta.persistence.MappedSuperclass;

import jakarta.persistence.PrePersist;
import lombok.Getter;
import lombok.Setter;
import jakarta.persistence.Id;

import lombok.Data;

@MappedSuperclass
@Getter
@Setter
public abstract class UUIDEntityWithoutTimestamp {
    @Id
    private UUID id; // Assume that we're using UUIDs for IDs

    @PrePersist
    public void prePersist() {
        if (id == null) {
            id = UUID.randomUUID();
        }
    }
}
