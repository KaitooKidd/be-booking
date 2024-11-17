package com.booking.users.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.booking.users.entity.UserEntity;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, String> {
    boolean existsByEmail(String email);

    void deleteByEmail(String email);

    @Transactional
    @Modifying
    @Query("DELETE FROM UserEntity u WHERE u.email IN (:emails)")
    void deleteAllByEmailIn(List<String> emails);

    void deleteByEmailIn(List<String> emails);

    Optional<UserEntity> findByEmail(String email);

    Optional<UserEntity> findByEmailAndVerified(String email, Boolean isVerified);

    @EntityGraph(attributePaths = {"customer", "hotelManager", "receptionist"})
    List<UserEntity> findAll();
}
