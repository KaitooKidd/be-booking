package com.booking.base.entity;

import java.util.UUID;

import jakarta.persistence.MappedSuperclass;

import org.springframework.data.annotation.Id;

import lombok.Data;

@MappedSuperclass
@Data
public abstract class UUIDEntityWithoutTimestamp {
    @Id
    private UUID id; // Assume that we're using UUIDs for IDs
}
