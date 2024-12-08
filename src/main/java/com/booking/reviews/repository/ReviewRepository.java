package com.booking.reviews.repository;

import com.booking.reviews.entity.ReviewEntity;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ReviewRepository extends JpaRepository<ReviewEntity, String> {
    @NotNull
    @EntityGraph(attributePaths = {"hotel", "room", "customer"})
    Optional<ReviewEntity> findById(@NotNull String uuId);

    @EntityGraph(attributePaths = {"hotel", "room", "customer"})
    ReviewEntity findByBookingId(String bookId);

    @EntityGraph(attributePaths = {"hotel", "room", "customer"})
    List<ReviewEntity> findAllByHotelId(Long hotelId);

    @EntityGraph(attributePaths = {"hotel", "room", "customer"})
    List<ReviewEntity> findAllByRoomId(Long roomId);

    @EntityGraph(attributePaths = {"hotel", "room", "customer"})
    List<ReviewEntity> findAllByCustomerEmail(String customerEmail);

}
