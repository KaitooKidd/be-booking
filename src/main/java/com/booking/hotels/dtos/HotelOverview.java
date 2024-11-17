package com.booking.hotels.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.Document;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class HotelOverview {
    private Document rooms;
    private Document reviews;

    public void setRooms(int total, Double minPrice) {
        rooms.append("total", total);
        rooms.append("minPrice", minPrice);
    }

    public void setReviews(int total, int average) {
        rooms.append("total", total);
        rooms.append("average", average);
    }
}
