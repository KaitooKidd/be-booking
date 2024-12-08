package com.booking.favorites.repository;

import java.util.List;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import com.booking.favorites.entity.FavoriteEntity;

public interface FavoriteRepository extends JpaRepository<FavoriteEntity, String> {
    @EntityGraph(attributePaths = {"hotel", "customer"})
    List<FavoriteEntity> findAllByCustomerEmail(String customerEmail);

    void deleteByCustomerEmailAndHotelId(String customerEmail, Long hotelId);
}
