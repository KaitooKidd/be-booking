package com.booking.base.entity;

import jakarta.persistence.*;

import com.booking.base.enums.GenderType;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;

@MappedSuperclass
@Getter
@Setter
public abstract class UBaseEntity extends TimestampEntity {
    @Id
    @Column(name = "id", nullable = false, unique = true)
    @JsonProperty("id")
    private String email; // email as the ID

    private String name;
    private String avatarKey;
    private String avatar;
    private String birthday;
    private String phone;

    @Enumerated(EnumType.STRING)
    private GenderType gender;
}
