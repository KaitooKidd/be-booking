package com.booking.base.entity;

import jakarta.persistence.MappedSuperclass;
import lombok.Data;
import org.springframework.data.annotation.Id;

import java.util.UUID;

@MappedSuperclass
@Data
public abstract class UUIDEntityWithoutTimestamp {
    @Id
    private UUID id; // Assume that we're using UUIDs for IDs
}
