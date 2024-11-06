package com.booking.users.entity;

import com.booking.base.entity.SequenceBaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
@Table(name = "permission")
public class PermissionEntity extends SequenceBaseEntity {
    @Column(name = "name", nullable = false, unique = true)
    String name;

    String description;
}
