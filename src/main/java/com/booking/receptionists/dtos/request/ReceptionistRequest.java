package com.booking.receptionists.dtos.request;

import com.booking.base.dtos.ProfileDTO;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@NoArgsConstructor
public class ReceptionistRequest extends ProfileDTO {
    private Long hotelId;
    private String password;
}
