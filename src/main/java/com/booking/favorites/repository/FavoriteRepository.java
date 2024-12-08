package com.booking.favorites.repository;

import com.booking.favorites.entity.FavoriteEntity;
import com.booking.reviews.entity.ReviewEntity;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface FavoriteRepository extends JpaRepository<FavoriteEntity, String> {
    @EntityGraph(attributePaths = {"hotel", "customer"})
    List<FavoriteEntity> findAllByCustomerEmail(String customerEmail);

    void deleteByCustomerEmailAndHotelId(String customerEmail, Long hotelId);
}
