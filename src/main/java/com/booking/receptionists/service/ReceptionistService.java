package com.booking.receptionists.service;

import com.booking.receptionists.dtos.request.ReceptionistRequest;
import com.booking.receptionists.dtos.response.HotelReceptionistResponse;
import com.booking.receptionists.dtos.response.ReceptionistResponse;
import com.booking.receptionists.entity.ReceptionistEntity;
import com.booking.users.dtos.request.UserRequest;

import java.util.List;

@SuppressWarnings("unUsed")
public interface ReceptionistService {
    ReceptionistEntity save(ReceptionistEntity receptionist);

    ReceptionistEntity getReceptionistByEmail(String email);

    ReceptionistResponse getReceptionistByEmailWithFetch(String email);

    List<HotelReceptionistResponse> getListReceptionistWithFetch(List<Long> hotelIds, UserRequest userRequest);

    ReceptionistEntity createReceptionist(UserRequest userRequest, ReceptionistRequest request);

    ReceptionistEntity updateReceptionist(UserRequest userRequest, ReceptionistRequest request);

    void deleteReceptionist(String email);
}
