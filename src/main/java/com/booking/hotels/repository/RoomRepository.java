package com.booking.hotels.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.booking.hotels.entity.RoomEntity;

public interface RoomRepository extends JpaRepository<RoomEntity, Long> {
    @Query("SELECT r FROM RoomEntity r WHERE r.hotelId = :hotelId")
    List<RoomEntity> findListHotelRooms(Long hotelId);
}
