package com.booking.receptionists.service.impl;

import com.booking.auth.exception.AppException;
import com.booking.auth.exception.ErrorCode;
import com.booking.base.utils.StringUtils;
import com.booking.customers.dtos.request.CustomerRequest;
import com.booking.customers.dtos.response.CustomerResponse;
import com.booking.customers.entity.CustomerEntity;
import com.booking.customers.helper.CustomerHelper;
import com.booking.hotels.service.HotelManagerService;
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
import com.booking.users.dtos.response.UserResponse;
import com.booking.users.entity.UserEntity;
import com.booking.users.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
@Log4j2
public class ReceptionistServiceImpl implements ReceptionistService {
    private final ReceptionistRepository receptionistRepository;
    private final UserService userService;
    private final ReceptionistMapper receptionistMapper;

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

        List<ReceptionistEntity> receptionistEntities = new ArrayList<>();
        if (user.getRole().getName().equalsIgnoreCase(RoleConstant.ADMIN_ROLE)) {
            receptionistEntities = receptionistRepository.findAll();
        }
        if (user.getRole().getName().equalsIgnoreCase(RoleConstant.HOTEL_MANAGER_ROLE)) {
            receptionistEntities = receptionistRepository.findAllByHotelManagerWithFetch(user.getEmail());
        }
//        List<ReceptionistEntity> receptionistEntities = receptionistRepository.findAllByHotelIdsWithFetch(hotelIds);
        Map<Long, List<ReceptionistEntity>> collect = receptionistEntities.stream().collect(Collectors.groupingBy(ReceptionistEntity::getHotelId));
        return collect.keySet().stream().map(hotelId -> HotelReceptionistResponse.builder()
                .id(hotelId)
                .name(collect.get(hotelId).get(0).getHotel().getName())
                .email(collect.get(hotelId).get(0).getHotel().getEmail())
                .receptionists(collect.get(hotelId).stream().map(receptionistMapper::toReceptionistResponse).toList())
                .build()).toList();
    }

    @Override
    public ReceptionistEntity createReceptionist(UserRequest userRequest, ReceptionistRequest request) {

        UserResponse userResponse = userService.getUserInfo(userRequest, null);
        if (userResponse.getRole().equals(RoleConstant.HOTEL_MANAGER_ROLE)
                && !userRequest.getEmail().equals(request.getEmail())) {
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

        UserResponse userResponse = userService.getUserInfo(userRequest, null);
        if (userResponse.getRole().equals(RoleConstant.HOTEL_MANAGER_ROLE)
                && !userRequest.getEmail().equals(request.getEmail())) {
            log.error("Hotel Manager can update receptionist info.");
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
