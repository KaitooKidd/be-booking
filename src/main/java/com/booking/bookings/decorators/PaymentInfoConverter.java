package com.booking.bookings.decorators;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

import com.booking.bookings.dtos.VnpayResultInfo;
import com.booking.utils.JsonUtils;

@Converter
public class PaymentInfoConverter implements AttributeConverter<VnpayResultInfo, String> {
    @Override
    public String convertToDatabaseColumn(VnpayResultInfo paymentInfo) {
        try {
            return JsonUtils.toString(paymentInfo);
        } catch (Exception e) {
            throw new IllegalArgumentException("Error converting payment info to JSON", e);
        }
    }

    @Override
    public VnpayResultInfo convertToEntityAttribute(String json) {
        try {
            return JsonUtils.getObject(json, VnpayResultInfo.class);
        } catch (Exception e) {
            throw new IllegalArgumentException("Error converting JSON to payment info", e);
        }
    }
}
