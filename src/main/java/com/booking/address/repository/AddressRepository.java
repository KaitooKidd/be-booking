package com.booking.address.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.booking.address.entity.AddressEntity;

public interface AddressRepository extends JpaRepository<AddressEntity, Long> {}
