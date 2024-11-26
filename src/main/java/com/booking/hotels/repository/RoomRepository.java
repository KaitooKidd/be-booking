package com.booking.hotels.repository;

import com.booking.hotels.entity.RoomEntity;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface RoomRepository extends JpaRepository<RoomEntity, Long> {
    @Query("SELECT r FROM RoomEntity r WHERE r.hotelId = :hotelId")
    List<RoomEntity> findListHotelRooms(Long hotelId);
}