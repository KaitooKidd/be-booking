package com.booking.receptionists.dtos.request;

import com.booking.base.dtos.ProfileDTO;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ReceptionistRequest extends ProfileDTO {
    private Long hotelId;
    private String password;
}
