package com.booking.bookings.decorators;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

import com.booking.bookings.dtos.PaymentInfo;
import com.booking.utils.JsonUtils;

@Converter
public class PaymentInfoConverter implements AttributeConverter<PaymentInfo, String> {
    @Override
    public String convertToDatabaseColumn(PaymentInfo timeRules) {
        try {
            return JsonUtils.toString(timeRules);
        } catch (Exception e) {
            throw new IllegalArgumentException("Error converting payment info to JSON", e);
        }
    }

    @Override
    public PaymentInfo convertToEntityAttribute(String json) {
        try {
            return JsonUtils.getObject(json, PaymentInfo.class);
        } catch (Exception e) {
            throw new IllegalArgumentException("Error converting JSON to payment info", e);
        }
    }
}
