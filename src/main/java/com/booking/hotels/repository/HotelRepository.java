package com.booking.hotels.repository;

import com.booking.hotels.entity.HotelEntity;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface HotelRepository extends JpaRepository<HotelEntity, Long> {
    @EntityGraph(attributePaths = {"manager", "address", "rooms"})
    HotelEntity findByEmail(String email);
    @EntityGraph(attributePaths = {"address", "rooms"})

    List<HotelEntity> findAll();
    @EntityGraph(attributePaths = {"rooms"})
    Optional<HotelEntity> findById(@NotNull Long id);
}