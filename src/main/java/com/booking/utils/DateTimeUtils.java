package com.booking.utils;

import jakarta.validation.ConstraintViolationException;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class DateTimeUtils {
    public static LocalDate toLocalDate(String date) {
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            return LocalDate.parse(date, formatter);
        } catch (Exception e) {
            throw new ConstraintViolationException("Date " + date + " must be in the format yyyy-MM-dd", null);
        }
    }
}
