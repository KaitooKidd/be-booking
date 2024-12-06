package com.booking.hotels.decorators;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

import com.booking.base.utils.JsonUtils;
import com.booking.hotels.dtos.TimeRules;

@Converter
public class TimeRulesConverter implements AttributeConverter<TimeRules, String> {
    @Override
    public String convertToDatabaseColumn(TimeRules timeRules) {
        try {
            return JsonUtils.toString(timeRules);
        } catch (Exception e) {
            throw new IllegalArgumentException("Error converting time rules to JSON", e);
        }
    }

    @Override
    public TimeRules convertToEntityAttribute(String json) {
        try {
            return JsonUtils.getObject(json, TimeRules.class);
        } catch (Exception e) {
            throw new IllegalArgumentException("Error converting JSON to time rules", e);
        }
    }
}
