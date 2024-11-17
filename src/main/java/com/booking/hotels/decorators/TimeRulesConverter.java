package com.booking.hotels.decorators;


import com.booking.base.utils.JsonUtils;
import com.booking.hotels.dtos.GalleryItem;
import com.booking.hotels.dtos.TimeRules;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

import java.sql.Time;
import java.util.List;

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

