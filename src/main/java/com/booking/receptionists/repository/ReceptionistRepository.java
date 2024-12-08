package com.booking.receptionists.repository;

import java.util.List;

import org.jetbrains.annotations.NotNull;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import com.booking.receptionists.entity.ReceptionistEntity;

public interface ReceptionistRepository extends JpaRepository<ReceptionistEntity, String> {
    @EntityGraph(attributePaths = {"user", "address", "hotel"})
    ReceptionistEntity findByEmail(String email);

    @NotNull
    @EntityGraph(attributePaths = {"user", "hotel"})
    List<ReceptionistEntity> findAll();

    @Query(
            "SELECT u FROM ReceptionistEntity u LEFT JOIN FETCH u.hotel JOIN FETCH u.user WHERE u.hotelId IN (:hotelIds)")
    List<ReceptionistEntity> findAllByHotelIdsWithFetch(List<Long> hotelIds);

    @Query(
            "SELECT u FROM ReceptionistEntity u LEFT JOIN FETCH u.hotel JOIN FETCH u.user WHERE u.hotel.email = :hotelManagerId")
    List<ReceptionistEntity> findAllByHotelManagerWithFetch(String hotelManagerId);

    @Transactional
    @Modifying
    @Query("DELETE FROM ReceptionistEntity u WHERE u.email IN (:emails)")
    void deleteAllByEmails(List<String> emails);
}
