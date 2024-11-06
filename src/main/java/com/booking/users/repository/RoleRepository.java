package com.booking.users.repository;

import com.booking.users.entity.RoleEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<RoleEntity, Long> {
    RoleEntity findByName(String roleName);
    void deleteByName(String roleName);
}
