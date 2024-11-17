package com.booking.hotels.decorators;


import com.booking.hotels.dtos.GalleryItem;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

import java.util.List;

@Converter
public class GalleryItemListConverter implements AttributeConverter<List<GalleryItem>, String> {

    private static final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public String convertToDatabaseColumn(List<GalleryItem> galleryItems) {
        try {
            return objectMapper.writeValueAsString(galleryItems);
        } catch (Exception e) {
            throw new IllegalArgumentException("Error converting gallery items to JSON", e);
        }
    }

    @Override
    public List<GalleryItem> convertToEntityAttribute(String json) {
        try {
            return objectMapper.readValue(json, new TypeReference<List<GalleryItem>>() {});
        } catch (Exception e) {
            throw new IllegalArgumentException("Error converting JSON to gallery items", e);
        }
    }
}

