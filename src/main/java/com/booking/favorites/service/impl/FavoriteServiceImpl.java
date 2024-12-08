package com.booking.favorites.service.impl;

import com.booking.favorites.entity.FavoriteEntity;
import com.booking.favorites.repository.FavoriteRepository;
import com.booking.favorites.service.FavoriteService;
import com.booking.hotels.dtos.response.HotelResponse;
import com.booking.hotels.mapper.HotelMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
@Log4j2
public class FavoriteServiceImpl implements FavoriteService {
    private final FavoriteRepository favoriteRepository;
    private final HotelMapper hotelMapper;

    @Override
    public List<HotelResponse> getListOfHotelsByCustomer(String customerId) {
        List<FavoriteEntity> allFavorites = favoriteRepository.findAllByCustomerEmail(customerId);
        return allFavorites.stream().map(f -> hotelMapper.toHotelResponse(f.getHotel())).toList();
    }

    @Override
    public void addFavorites(String customerEmail, List<String> hotelId) {
        for (String id : hotelId) {
            favoriteRepository.save(FavoriteEntity.builder()
                    .customerEmail(customerEmail)
                    .hotelId(Long.parseLong(id))
                    .build());
        }
    }

    @Override
    public void deleteFavorites(String customerEmail, String hotelId) {
        favoriteRepository.deleteByCustomerEmailAndHotelId(customerEmail, Long.valueOf(hotelId));
    }
}
