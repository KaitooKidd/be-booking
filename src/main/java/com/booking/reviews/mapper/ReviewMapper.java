package com.booking.reviews.mapper;

import org.mapstruct.Builder;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

import com.booking.reviews.dtos.request.ReviewRequest;
import com.booking.reviews.dtos.response.ReviewResponse;
import com.booking.reviews.entity.ReviewEntity;

@Mapper(componentModel = "spring", builder = @Builder(disableBuilder = true))
public interface ReviewMapper {
    ReviewEntity toEntity(ReviewRequest request);

    ReviewResponse toResponse(ReviewEntity entity);

    void updateEntity(@MappingTarget ReviewEntity entity, ReviewRequest request);
}
