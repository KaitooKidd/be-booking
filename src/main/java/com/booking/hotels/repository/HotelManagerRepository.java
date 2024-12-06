package com.booking.hotels.repository;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import com.booking.hotels.entity.HotelManagerEntity;

public interface HotelManagerRepository extends JpaRepository<HotelManagerEntity, String> {
    @EntityGraph(attributePaths = {"user", "address"})
    HotelManagerEntity findByEmail(String email);
}
