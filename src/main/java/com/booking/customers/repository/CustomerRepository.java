package com.booking.customers.repository;

import java.util.List;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import com.booking.customers.entity.CustomerEntity;

public interface CustomerRepository extends JpaRepository<CustomerEntity, String> {
    @EntityGraph(attributePaths = {"user", "address"})
    CustomerEntity findByEmail(String email);

    @Query("SELECT u FROM CustomerEntity u LEFT JOIN FETCH u.address JOIN FETCH u.user WHERE u.email IN (:emails)")
    List<CustomerEntity> findAllByEmailsWithFetch(List<String> emails);

    @Query("SELECT u FROM CustomerEntity u LEFT JOIN FETCH u.address JOIN FETCH u.user")
    List<CustomerEntity> findAllByWithFetch();

    @Transactional
    @Modifying
    @Query("DELETE FROM CustomerEntity u WHERE u.email IN (:emails)")
    void deleteAllByEmails(List<String> emails);
}
