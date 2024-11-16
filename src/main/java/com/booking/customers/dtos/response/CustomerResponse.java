package com.booking.customers.dtos.response;

import com.booking.customers.entity.CustomerEntity;
import com.booking.users.entity.UserEntity;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;

import java.util.Date;


@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CustomerResponse extends CustomerEntity {

    @JsonIgnore
    private UserEntity user;
    @JsonProperty("isVerified")
    public Boolean getIsVerified() {
        return user != null && user.isVerified();
    }
    private Date createdAt;
}
