package com.booking.hotels.repository;

import java.util.List;
import java.util.Optional;

import org.jetbrains.annotations.NotNull;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import com.booking.hotels.entity.HotelEntity;
import org.springframework.data.jpa.repository.Query;

public interface HotelRepository extends JpaRepository<HotelEntity, Long> {
    @EntityGraph(attributePaths = {"manager", "address", "rooms"})
    HotelEntity findByEmail(String email);

    @NotNull
    @EntityGraph(attributePaths = {"address", "rooms"})
    List<HotelEntity> findAll();

    @NotNull
    @EntityGraph(attributePaths = {"rooms"})
    Optional<HotelEntity> findById(@NotNull Long id);
}
