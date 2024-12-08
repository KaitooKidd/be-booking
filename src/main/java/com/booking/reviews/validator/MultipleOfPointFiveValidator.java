package com.booking.reviews.validator;

import com.booking.reviews.interfaces.MultipleOfPointFive;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class MultipleOfPointFiveValidator implements ConstraintValidator<MultipleOfPointFive, Double> {

    @Override
    public void initialize(MultipleOfPointFive constraintAnnotation) {
    }

    @Override
    public boolean isValid(Double value, ConstraintValidatorContext context) {
        if (value == null) {
            return true; // @NotNull should handle null checks
        }
        return value % 0.5 == 0;
    }
}