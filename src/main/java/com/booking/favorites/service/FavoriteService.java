package com.booking.favorites.service;

import com.booking.hotels.dtos.response.HotelResponse;

import java.util.List;

public interface FavoriteService {
    List<HotelResponse> getListOfHotelsByCustomer(String customerId);

    void addFavorites(String customerEmail, List<String> hotelId);

    void deleteFavorites(String customerEmail, String hotelId);
}
