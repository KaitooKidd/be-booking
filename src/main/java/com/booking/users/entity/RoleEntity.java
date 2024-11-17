package com.booking.users.entity;

import com.booking.base.entity.SequenceBaseEntity;
import com.booking.users.enums.RoleTypes;
import jakarta.persistence.*;
import lombok.*;

import java.util.Set;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@SuppressWarnings("ALL")
@Entity
@Table(name = "roles")
public class RoleEntity extends SequenceBaseEntity {
    @Column(name = "name", nullable = false, unique = true)
    private String name;
    private String description;

    @OneToMany(mappedBy = "role", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<UserEntity> users;
}
