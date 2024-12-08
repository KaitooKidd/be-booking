package com.booking.reviews.interfaces;

import com.booking.reviews.validator.MultipleOfPointFiveValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = MultipleOfPointFiveValidator.class)
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface MultipleOfPointFive {

    String message() default "The value must be a multiple of 0.5";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}

