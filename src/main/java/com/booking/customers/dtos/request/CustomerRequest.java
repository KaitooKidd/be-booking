package com.booking.customers.dtos.request;

import com.booking.base.dtos.ProfileDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@NoArgsConstructor
public class CustomerRequest extends ProfileDTO {
}
