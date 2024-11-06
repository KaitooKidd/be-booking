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

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = "role_permissions",
            joinColumns = @JoinColumn(name = "role_id"),
            inverseJoinColumns = @JoinColumn(name = "permission_id")
    )
    private Set<PermissionEntity> permissions;
    @OneToMany(mappedBy = "role", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<UserEntity> users;
}
