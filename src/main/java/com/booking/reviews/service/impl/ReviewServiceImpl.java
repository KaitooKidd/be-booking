package com.booking.reviews.service.impl;

import com.booking.auth.exception.AppException;
import com.booking.auth.exception.ErrorCode;
import com.booking.reviews.dtos.request.ReviewRequest;
import com.booking.reviews.dtos.response.ReviewResponse;
import com.booking.reviews.entity.ReviewEntity;
import com.booking.reviews.mapper.ReviewMapper;
import com.booking.reviews.repository.ReviewRepository;
import com.booking.reviews.service.ReviewService;
import com.booking.users.constant.RoleConstant;
import com.booking.users.dtos.request.UserRequest;
import com.booking.users.dtos.response.UserResponse;
import com.booking.users.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
@Log4j2
public class ReviewServiceImpl implements ReviewService {
    private final ReviewRepository reviewRepository;
    private final ReviewMapper reviewMapper;
    private final UserService userService;

    @Override
    public ReviewEntity save(ReviewEntity reviewEntity) {
        return reviewRepository.save(reviewEntity);
    }

    @Override
    public ReviewResponse createReview(UserRequest userRequest, ReviewRequest request) {

        ReviewEntity reviewEntity = reviewRepository.findByBookingId(request.getBookingId());
        if (reviewEntity!= null) {
            String message = String.format("Review for booking id %s already exists.", request.getBookingId());
            log.error(message);
            throw new AppException(message, ErrorCode.USER_NOT_EXISTED);
        }

        // TODO: 12/7/2024 Create new review

        return null;
    }

    @Override
    public ReviewResponse updateReview(String reviewId, ReviewRequest request, UserRequest userRequest) {

        ReviewEntity reviewEntity = reviewRepository.findById(reviewId).orElse(null);
        if (reviewEntity == null) {
            String message = String.format("Review for review id %s not exists.", reviewId);
            log.error(message);
            throw new AppException(message, ErrorCode.USER_NOT_EXISTED);
        }

        UserResponse userResponse = userService.getUserInfo(userRequest, null);
        if (userResponse.getRole().equals(RoleConstant.CUSTOMER_ROLE)
        && !userRequest.getEmail().equals(reviewEntity.getCustomerEmail())) {
            String message = "Admin and Owner Review can update review.";
            log.error(message);
            throw new AppException(message, ErrorCode.UNAUTHORIZED);
        }

        reviewMapper.updateEntity(reviewEntity, request);
        return reviewMapper.toResponse(save(reviewEntity));
    }

    @Override
    public List<ReviewResponse> getAllByHotelId(Long hotelId) {
        List<ReviewEntity> list = reviewRepository.findAllByHotelId(hotelId);
        return list.stream().map(reviewMapper::toResponse).toList();
    }

    @Override
    public List<ReviewResponse> getAllByRoomId(Long roomId) {
        List<ReviewEntity> list = reviewRepository.findAllByRoomId(roomId);
        return list.stream().map(reviewMapper::toResponse).toList();
    }
    @Override
    public List<ReviewResponse> getAllByCustomerId(String customerEmail) {
        List<ReviewEntity> list = reviewRepository.findAllByCustomerEmail(customerEmail);
        return list.stream().map(reviewMapper::toResponse).toList();
    }

}
