package com.booking.bookings.repository;

import java.util.List;
import java.util.Optional;

import org.jetbrains.annotations.NotNull;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import com.booking.bookings.entity.BookingEntity;

public interface BookingRepository extends JpaRepository<BookingEntity, String> {

    List<BookingEntity> findAllByHotelIdAndRoomIdAndCustomerEmail(Long hotelId, Long RoomId, String customerEmail);

    @NotNull
    @EntityGraph(attributePaths = {"hotel", "room", "customer", "review"})
    Optional<BookingEntity> findById(@NotNull String uuId);

    BookingEntity findByPaymentId(String paymentId);

    @EntityGraph(attributePaths = {"hotel", "room", "customer", "review"})
    List<BookingEntity> findAllByHotelOwnerEmail(String hotelOwnerEmail);

    @NotNull
    @EntityGraph(attributePaths = {"hotel", "room", "customer", "review"})
    List<BookingEntity> findAll();

    @EntityGraph(attributePaths = {"hotel", "room", "customer", "review"})
    List<BookingEntity> findAllByCustomerEmail(String customerEmail);
}
