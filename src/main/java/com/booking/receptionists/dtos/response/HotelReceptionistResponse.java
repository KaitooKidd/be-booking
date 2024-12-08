package com.booking.receptionists.dtos.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class HotelReceptionistResponse {
    private Long id;
    private String email;
    private String name;
    private List<ReceptionistResponse> receptionists;
}
