package com.booking.receptionists.service.impl;

import java.util.*;

import org.springframework.stereotype.Service;

import com.booking.auth.exception.AppException;
import com.booking.auth.exception.ErrorCode;
import com.booking.hotels.entity.HotelEntity;
import com.booking.hotels.repository.HotelRepository;
import com.booking.receptionists.dtos.request.ReceptionistRequest;
import com.booking.receptionists.dtos.response.HotelReceptionistResponse;
import com.booking.receptionists.dtos.response.ReceptionistResponse;
import com.booking.receptionists.entity.ReceptionistEntity;
import com.booking.receptionists.mapper.ReceptionistMapper;
import com.booking.receptionists.repository.ReceptionistRepository;
import com.booking.receptionists.service.ReceptionistService;
import com.booking.users.constant.RoleConstant;
import com.booking.users.dtos.request.UserCreationRequest;
import com.booking.users.dtos.request.UserRequest;
import com.booking.users.entity.UserEntity;
import com.booking.users.service.UserService;
import com.booking.utils.StringUtils;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@RequiredArgsConstructor
@Service
@Log4j2
public class ReceptionistServiceImpl implements ReceptionistService {
    private final ReceptionistRepository receptionistRepository;
    private final UserService userService;
    private final ReceptionistMapper receptionistMapper;

    private final HotelRepository hotelRepository;

    @Override
    public ReceptionistEntity save(ReceptionistEntity receptionist) {
        return receptionistRepository.save(receptionist);
    }

    @Override
    public ReceptionistEntity getReceptionistByEmail(String email) {
        Optional<ReceptionistEntity> receptionist = receptionistRepository.findById(email);
        if (receptionist.isEmpty()) {
            throw new RuntimeException("Receptionist not found");
        }
        return receptionist.get();
    }

    @Override
    public ReceptionistResponse getReceptionistByEmailWithFetch(String email) {
        ReceptionistEntity receptionist = receptionistRepository.findByEmail(email);
        if (receptionist == null) {
            throw new RuntimeException("Receptionist not found");
        }
        return receptionistMapper.toReceptionistResponse(receptionist);
    }

    @Override
    public List<HotelReceptionistResponse> getListReceptionistWithFetch(List<Long> hotelIds, UserRequest userRequest) {
        UserEntity user = userService.getUserByEmail(userRequest.getEmail(), null);
        if (user == null) {
            throw new RuntimeException("User not found");
        }

        List<HotelEntity> hotelsEntities = new ArrayList<>();
        if (user.getRole().getName().equalsIgnoreCase(RoleConstant.ADMIN_ROLE)) {
            hotelsEntities = hotelRepository.findAllHotelsWithReceptionists();
        } else if (user.getRole().getName().equalsIgnoreCase(RoleConstant.HOTEL_MANAGER_ROLE)) {
            hotelsEntities = hotelRepository.findByEmailWithReceptionists(user.getEmail());
        }

        return hotelsEntities.stream()
                .map(hotel -> HotelReceptionistResponse.builder()
                        .id(hotel.getId())
                        .name(hotel.getName())
                        .email(hotel.getEmail())
                        .receptionists(hotel.getReceptionists().stream()
                                .map(receptionistMapper::toReceptionistResponse)
                                .toList())
                        .build())
                .toList();
    }

    @Override
    public ReceptionistEntity createReceptionist(UserRequest userRequest, ReceptionistRequest request) {

        UserEntity userEntity = userService.getUserByEmail(userRequest.getEmail(), null);
        Long id = userEntity.getHotelManager().getHotel().getId();
        if (userEntity.getRole().getName().equals(RoleConstant.HOTEL_MANAGER_ROLE)
                && !Objects.equals(id, request.getHotelId())) {
            log.error("Hotel Manager can create receptionist.");
            throw new AppException(ErrorCode.UNAUTHORIZED);
        }

        UserEntity user = userService.createUser(UserCreationRequest.builder()
                .email(request.getEmail())
                .roleName(RoleConstant.RECEPTIONIST_ROLE)
                .isVerified(false)
                .shouldCreateFirebaseUser(true)
                .build());

        ReceptionistEntity receptionist = receptionistMapper.toReceptionist(request);
        receptionist.setEmail(request.getEmail());
        receptionist.setName(
                StringUtils.isExist(request.getName())
                        ? request.getName()
                        : StringUtils.getEmailName(request.getEmail()));
        receptionist.setUser(user);

        return save(receptionist);
    }

    @Override
    public ReceptionistEntity updateReceptionist(UserRequest userRequest, ReceptionistRequest request) {

        ReceptionistEntity receptionist = receptionistRepository.findByEmail(request.getEmail());

        if (receptionist == null) {
            log.error("Receptionist not found to update.");
            throw new AppException(ErrorCode.USER_EXISTED);
        }

        UserEntity userEntity = userService.getUserByEmail(userRequest.getEmail(), null);
        Long id = userEntity.getHotelManager().getHotel().getId();
        if (userEntity.getRole().getName().equals(RoleConstant.HOTEL_MANAGER_ROLE)
                && !Objects.equals(id, request.getHotelId())) {
            log.error("Hotel Manager can create receptionist.");
            throw new AppException(ErrorCode.UNAUTHORIZED);
        }

        receptionistMapper.updateReceptionist(receptionist, request);
        return save(receptionist);
    }

    @Override
    public void deleteReceptionist(String email) {
        receptionistRepository.deleteAllByEmails(new ArrayList<>(List.of(email)));
    }
}
