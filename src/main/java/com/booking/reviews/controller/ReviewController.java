package com.booking.reviews.controller;

import com.booking.reviews.dtos.request.ReviewRequest;
import com.booking.reviews.dtos.response.ReviewResponse;
import com.booking.reviews.service.ReviewService;
import com.booking.users.dtos.request.UserRequest;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/reviews")
@Tag(name = "Reviews")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Log4j2
public class ReviewController {
    ReviewService reviewService;

    @PatchMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('admin','customer')")
    ReviewResponse updateReview(
            @PathVariable("id") String id,
            @AuthenticationPrincipal UserRequest userRequest, @RequestBody ReviewRequest reviewRequest) {
        return reviewService.updateReview(id, reviewRequest, userRequest);
    }

    @PostMapping("")
    @PreAuthorize("hasAnyAuthority('admin','customer')")
    ReviewResponse createReview(
            @AuthenticationPrincipal UserRequest userRequest, @RequestBody ReviewRequest reviewRequest) {
        return reviewService.createReview(userRequest, reviewRequest);
    }

    @GetMapping("/hotels/{id}")
    List<ReviewResponse> listByHotelId(@PathVariable("id") String id) {
        return reviewService.getAllByHotelId(Long.valueOf(id));
    }

    @GetMapping("/rooms/{id}")
    List<ReviewResponse> listByRoomId(@PathVariable("id") String id) {
        return reviewService.getAllByRoomId(Long.valueOf(id));
    }

    @GetMapping("/customers")
    List<ReviewResponse> listByCustomer(@AuthenticationPrincipal UserRequest userRequest) {
        return reviewService.getAllByCustomerId(userRequest.getEmail());
    }
}
