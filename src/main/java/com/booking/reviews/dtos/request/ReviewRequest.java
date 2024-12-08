package com.booking.reviews.dtos.request;

import com.booking.reviews.interfaces.MultipleOfPointFive;
import jakarta.validation.constraints.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@NoArgsConstructor
public class ReviewRequest {
    @NotEmpty
    @NotBlank
    private String bookingId;

    @NotNull
    @DecimalMin("0.0")
    @DecimalMax("5.0")
    @MultipleOfPointFive
    private Double staffRating;

    @NotNull
    @DecimalMin("0.0")
    @DecimalMax("5.0")
    private Double facilityRating;

    @NotNull
    @DecimalMin("0.0")
    @DecimalMax("5.0")
    private Double cleanlinessRating;

    @NotNull
    @DecimalMin("0.0")
    @DecimalMax("5.0")
    private Double comfortRating;

    @NotNull
    @DecimalMin("0.0")
    @DecimalMax("5.0")
    private Double valueForMoneyRating;

    @NotNull
    @DecimalMin("0.0")
    @DecimalMax("5.0")
    private Double locationRating;

    @NotEmpty
    @NotBlank
    private String comment;

    // In Java, you can use a List<String> for an array of strings.
    // private List<String> reviewImages;

}
