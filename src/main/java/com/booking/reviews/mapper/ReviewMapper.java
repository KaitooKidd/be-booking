package com.booking.reviews.mapper;

import com.booking.reviews.dtos.request.ReviewRequest;
import com.booking.reviews.dtos.response.ReviewResponse;
import com.booking.reviews.entity.ReviewEntity;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface ReviewMapper {
    ReviewEntity toEntity(ReviewRequest request);
    ReviewResponse toResponse(ReviewEntity entity);
    void updateEntity(@MappingTarget ReviewEntity entity, ReviewRequest request);
}
