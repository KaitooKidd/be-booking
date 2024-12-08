package com.booking.favorites.controller;

import java.util.List;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import com.booking.auth.dto.response.ApiResponse;
import com.booking.favorites.dtos.request.AddFavoritesRequest;
import com.booking.favorites.service.FavoriteService;
import com.booking.hotels.dtos.response.HotelResponse;
import com.booking.users.dtos.request.UserRequest;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.log4j.Log4j2;

@RestController
@RequestMapping("/favorites")
@Tag(name = "Favorites")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Log4j2
public class FavoriteController {
    FavoriteService favoriteService;

    @DeleteMapping("/customers/{hotelId}")
    @PreAuthorize("hasAnyAuthority('admin','customer')")
    ApiResponse<Void> deleteFavorites(
            @PathVariable("hotelId") String hotelId, @AuthenticationPrincipal UserRequest userRequest) {
        favoriteService.deleteFavorites(userRequest.getEmail(), hotelId);
        return ApiResponse.<Void>builder()
                .message("Delete favorites success")
                .code(1000)
                .build();
    }

    @PostMapping("/customers")
    @PreAuthorize("hasAnyAuthority('admin','customer')")
    ApiResponse<Void> addFavorites(
            @AuthenticationPrincipal UserRequest userRequest, @RequestBody AddFavoritesRequest request) {
        favoriteService.addFavorites(userRequest.getEmail(), request.getHotelIds());
        return ApiResponse.<Void>builder()
                .message("Add favorites success")
                .code(1000)
                .build();
    }

    @GetMapping("/customers")
    @PreAuthorize("hasAnyAuthority('customer')")
    List<HotelResponse> listByCustomer(@AuthenticationPrincipal UserRequest userRequest) {
        return favoriteService.getListOfHotelsByCustomer(userRequest.getEmail());
    }
}
