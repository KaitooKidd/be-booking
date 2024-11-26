package com.booking.hotels.repository;

import com.booking.hotels.entity.HotelManagerEntity;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HotelManagerRepository extends JpaRepository<HotelManagerEntity, String> {
    @EntityGraph(attributePaths = {"user", "address"})
    HotelManagerEntity findByEmail(String email);
}