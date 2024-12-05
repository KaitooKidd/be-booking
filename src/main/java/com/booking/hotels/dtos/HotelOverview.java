package com.booking.hotels.dtos;

import org.bson.Document;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class HotelOverview {
    private Document rooms = new Document();
    private Document reviews = new Document();

    public void setRooms(int total, Double minPrice) {
        rooms.append("total", total);
        rooms.append("minPrice", minPrice);
    }

    public void setReviews(int total, int average) {
        reviews.append("total", total);
        reviews.append("average", average);
    }
}
