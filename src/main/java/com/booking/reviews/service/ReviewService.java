package com.booking.reviews.service;

import java.util.List;

import com.booking.reviews.dtos.request.ReviewRequest;
import com.booking.reviews.dtos.response.ReviewResponse;
import com.booking.reviews.entity.ReviewEntity;
import com.booking.users.dtos.request.UserRequest;

@SuppressWarnings("unused")
public interface ReviewService {
    ReviewEntity save(ReviewEntity reviewEntity);

    ReviewResponse createReview(UserRequest userRequest, ReviewRequest request);

    ReviewResponse updateReview(String reviewId, ReviewRequest request, UserRequest userRequest);

    List<ReviewResponse> getAllByHotelId(Long hotelId);

    List<ReviewResponse> getAllByRoomId(Long roomId);

    List<ReviewResponse> getAllByCustomerId(String customerEmail);
}
