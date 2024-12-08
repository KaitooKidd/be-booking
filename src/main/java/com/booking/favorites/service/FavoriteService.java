package com.booking.favorites.service;

import java.util.List;

import com.booking.hotels.dtos.response.HotelResponse;

public interface FavoriteService {
    List<HotelResponse> getListOfHotelsByCustomer(String customerId);

    void addFavorites(String customerEmail, List<String> hotelId);

    void deleteFavorites(String customerEmail, String hotelId);
}
