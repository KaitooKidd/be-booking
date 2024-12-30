package com.booking.receptionists.controller;

import java.util.List;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import com.booking.auth.dto.response.ApiResponse;
import com.booking.receptionists.dtos.request.ReceptionistRequest;
import com.booking.receptionists.dtos.response.HotelReceptionistResponse;
import com.booking.receptionists.dtos.response.ReceptionistResponse;
import com.booking.receptionists.mapper.ReceptionistMapper;
import com.booking.receptionists.service.ReceptionistService;
import com.booking.users.dtos.request.UserRequest;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.log4j.Log4j2;

@RestController
@RequestMapping("/receptionists")
@Tag(name = "Receptionists")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Log4j2
public class ReceptionistController {
    ReceptionistService receptionistService;
    ReceptionistMapper receptionistMapper;

    @PatchMapping("")
    @PreAuthorize("hasAnyAuthority('admin','hotel_manager')")
    ReceptionistResponse updateReceptionist(
            @AuthenticationPrincipal UserRequest userRequest, @RequestBody ReceptionistRequest receptionistRequest) {
        return receptionistMapper.toReceptionistResponse(
                receptionistService.updateReceptionist(userRequest, receptionistRequest));
    }

    @PostMapping("")
    @PreAuthorize("hasAnyAuthority('admin','hotel_manager')")
    ReceptionistResponse createReceptionist(
            @AuthenticationPrincipal UserRequest userRequest, @RequestBody ReceptionistRequest receptionistRequest) {
        return receptionistMapper.toReceptionistResponse(
                receptionistService.createReceptionist(userRequest, receptionistRequest));
    }

    @GetMapping("/me")
    @PreAuthorize("hasAnyAuthority('receptionist')")
    ReceptionistResponse getCurrentInfo(@AuthenticationPrincipal UserRequest userRequest) {
        return receptionistService.getReceptionistByEmailWithFetch(userRequest.getEmail());
    }

    @GetMapping("")
    @PreAuthorize("hasAnyAuthority('admin','hotel_manager')")
    List<HotelReceptionistResponse> listReceptionist(
            @RequestParam(required = false) List<Long> hotelIds, @AuthenticationPrincipal UserRequest userRequest) {
        return receptionistService.getListReceptionistWithFetch(hotelIds, userRequest);
    }

    @DeleteMapping("")
    @PreAuthorize("hasAnyAuthority('admin','hotel_manager')")
    ApiResponse<Void> deleteReceptionist(@RequestParam String email) {
        receptionistService.deleteReceptionist(email);
        return ApiResponse.<Void>builder().build();
    }
}
