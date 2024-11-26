package com.booking.hotels.controller;

import com.booking.auth.dto.response.ApiResponse;
import com.booking.customers.dtos.request.DeleteCustomerRequest;
import com.booking.customers.dtos.request.GetListCustomerRequest;
import com.booking.customers.dtos.response.CustomerResponse;
import com.booking.customers.helper.CustomerHelper;
import com.booking.hotels.dtos.request.HotelManagerRequest;
import com.booking.hotels.dtos.request.HotelRequest;
import com.booking.hotels.dtos.request.RoomRequest;
import com.booking.hotels.dtos.response.HotelManagerResponse;
import com.booking.hotels.dtos.response.HotelResponse;
import com.booking.hotels.dtos.response.RoomResponse;
import com.booking.hotels.mapper.HotelMapper;
import com.booking.hotels.mapper.RoomMapper;
import com.booking.hotels.service.HotelManagerService;
import com.booking.hotels.service.HotelService;
import com.booking.hotels.service.RoomService;
import com.booking.users.dtos.request.UserRequest;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/hotels")
@Tag(name = "Hotels")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Log4j2
public class HotelController {
    HotelService hotelService;
    RoomService roomService;
    HotelManagerService hotelManagerService;
    HotelMapper hotelMapper;
    RoomMapper roomMapper;

    @PatchMapping("/manager")
    @PreAuthorize("hasAnyAuthority('admin','hotel_manager')")
    HotelManagerResponse updateHotelManager(
            @AuthenticationPrincipal UserRequest userRequest, @RequestBody HotelManagerRequest hotelManagerRequest) {
        return hotelManagerService.updateHotelManager(userRequest, hotelManagerRequest);
    }

    @GetMapping("/manager/me")
    @PreAuthorize("hasAnyAuthority('admin','hotel_manager')")
    HotelManagerResponse getHotelManager(@AuthenticationPrincipal UserRequest userRequest) {
        return hotelManagerService.getHotelManagerByEmail(userRequest.getEmail());
    }

    @PostMapping("")
    HotelResponse createHotel(@RequestBody HotelRequest hotelRequest) {
        return hotelService.createHotel(hotelRequest);
    }

    @GetMapping("/me")
//    @PreAuthorize("hasAnyAuthority('admin','hotel_manager')")
    HotelResponse getCurrentHotel(@AuthenticationPrincipal UserRequest userRequest) {
        return hotelService.getMyHotel(userRequest);
    }

    @PatchMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('admin','hotel_manager')")
    HotelResponse updateHotel(
            @AuthenticationPrincipal UserRequest userRequest,
            @RequestBody HotelRequest hotelRequest,
            @PathVariable("id") Long id) {
        return hotelService.updateHotel(id, userRequest, hotelRequest);
    }
    @GetMapping("/{id}")
    HotelResponse getHotel(@PathVariable("id") Long id) {
        return hotelMapper.toHotelResponse(hotelService.getHotelById(id));
    }

    @GetMapping("")
    List<HotelResponse> listHotels() {
        return hotelService.findAll();
    }

    @PostMapping("/{id}/rooms")
    @PreAuthorize("hasAnyAuthority('admin','hotel_manager')")
    RoomResponse createRoom(@AuthenticationPrincipal UserRequest userRequest,
                            @RequestBody RoomRequest request,
                            @PathVariable("id") Long id) {
        return roomMapper.toRoomResponse(roomService.createRoom(id, userRequest, request));
    }

    @PatchMapping("/{id}/rooms/{roomId}")
    @PreAuthorize("hasAnyAuthority('admin','hotel_manager')")
    RoomResponse updateRoom(@RequestBody RoomRequest request,
                            @PathVariable("roomId") Long roomId, @PathVariable Long id) {
        return roomMapper.toRoomResponse(roomService.updateRoom(roomId, request));
    }
    @DeleteMapping("/{id}/rooms/{roomId}")
    @PreAuthorize("hasAnyAuthority('admin', 'hotel_manager')")
    ApiResponse<Void> deleteRoom(@PathVariable Long id, @PathVariable Long roomId) {
        roomService.deleteRoom(roomId);
        return ApiResponse.<Void>builder().build();
    }
}
