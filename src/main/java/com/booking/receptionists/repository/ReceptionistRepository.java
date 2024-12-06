package com.booking.receptionists.repository;

import java.util.List;

import com.booking.receptionists.entity.ReceptionistEntity;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import com.booking.customers.entity.CustomerEntity;

public interface ReceptionistRepository extends JpaRepository<ReceptionistEntity, String> {
    @EntityGraph(attributePaths = {"user", "address", "hotel"})
    ReceptionistEntity findByEmail(String email);

    @Query("SELECT u FROM ReceptionistEntity u LEFT JOIN FETCH u.hotel JOIN FETCH u.user WHERE u.hotelId IN (:hotelIds)")
    List<ReceptionistEntity> findAllByHotelIdsWithFetch(List<Long> hotelIds);

    @Query("SELECT u FROM ReceptionistEntity u LEFT JOIN FETCH u.address JOIN FETCH u.user")
    List<CustomerEntity> findAllByWithFetch();

    @Transactional
    @Modifying
    @Query("DELETE FROM ReceptionistEntity u WHERE u.email IN (:emails)")
    void deleteAllByEmails(List<String> emails);
}
