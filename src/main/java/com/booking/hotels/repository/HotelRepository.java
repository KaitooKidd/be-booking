package com.booking.hotels.repository;

import java.util.List;
import java.util.Optional;

import org.jetbrains.annotations.NotNull;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.booking.hotels.entity.HotelEntity;

public interface HotelRepository extends JpaRepository<HotelEntity, Long> {
    @EntityGraph(attributePaths = {"manager", "address", "rooms"})
    HotelEntity findByEmail(String email);

    @EntityGraph(attributePaths = "receptionists")
    @Query("SELECT h FROM HotelEntity h where h.email = :email")
    List<HotelEntity> findByEmailWithReceptionists(String email);

    @EntityGraph(attributePaths = "receptionists")
    @Query("SELECT h FROM HotelEntity h")
    List<HotelEntity> findAllHotelsWithReceptionists();

    @NotNull
    @EntityGraph(attributePaths = {"address", "rooms"})
    List<HotelEntity> findAll();

    @NotNull
    @EntityGraph(attributePaths = {"rooms"})
    Optional<HotelEntity> findById(@NotNull Long id);
}
