package com.booking.users.entity;

import java.util.Set;

import jakarta.persistence.*;

import com.booking.base.entity.SequenceBaseEntity;
import com.fasterxml.jackson.annotation.JsonBackReference;

import lombok.*;

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

    @Column(columnDefinition = "TEXT")
    private String description;

    @OneToMany(mappedBy = "role", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonBackReference
    private Set<UserEntity> users;
}
