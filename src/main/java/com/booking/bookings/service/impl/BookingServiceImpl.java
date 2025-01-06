package com.booking.bookings.service.impl;

import java.util.Comparator;
import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Service;

import com.booking.auth.exception.AppException;
import com.booking.auth.exception.ErrorCode;
import com.booking.bookings.dtos.PaymentInfo;
import com.booking.bookings.dtos.request.BookingRequest;
import com.booking.bookings.dtos.request.UpdateBookingStatusRequest;
import com.booking.bookings.dtos.response.BookingResponse;
import com.booking.bookings.entity.BookingEntity;
import com.booking.bookings.enums.BookingStatus;
import com.booking.bookings.enums.PaymentChannel;
import com.booking.bookings.mapper.BookingMapper;
import com.booking.bookings.repository.BookingRepository;
import com.booking.bookings.service.BookingService;
import com.booking.hotels.entity.HotelEntity;
import com.booking.hotels.service.HotelService;
import com.booking.users.constant.RoleConstant;
import com.booking.users.dtos.request.UserRequest;
import com.booking.users.dtos.response.UserResponse;
import com.booking.users.service.UserService;
import com.booking.utils.DateTimeUtils;
import com.booking.utils.StringUtils;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Service
@Log4j2
@RequiredArgsConstructor
public class BookingServiceImpl implements BookingService {
    private static final String REDIS_PREFIX = "pending_booked";

    private final BookingRepository bookingRepository;
    private final BookingMapper bookingMapper;
    private final UserService userService;
    private final HotelService hotelService;
    private final PendingBookingService pendingBookingService;

    @Override
    public BookingEntity getBookingById(String id, Boolean isPaid) {
        BookingEntity bookingEntity = bookingRepository.findById(id).orElse(null);
        if (bookingEntity == null) {
            String message = String.format("Booking %s not found", id);
            log.error(message);
            throw new AppException(message, ErrorCode.USER_NOT_EXISTED);
        }
        if (isPaid != null && bookingEntity.isPaid() != isPaid) {
            String message = String.format("Booking %s payment status is %s", id, isPaid ? "paid" : "unpaid");
            log.error(message);
            throw new AppException(message, ErrorCode.USER_NOT_EXISTED);
        }
        return bookingEntity;
    }

    @Override
    public BookingEntity getBookingByPaymentId(String paymentId) {
        BookingEntity bookingEntity = bookingRepository.findByPaymentId(paymentId);
        if (bookingEntity == null) {
            String message = String.format("Booking with payment id %s not found", paymentId);
            log.error(message);
            throw new AppException(message, ErrorCode.USER_NOT_EXISTED);
        }
        return bookingEntity;
    }

    @Override
    public BookingEntity save(BookingEntity reviewEntity) {
        return bookingRepository.save(reviewEntity);
    }

    @Override
    public void updateBookingStatus(String bookingId, UpdateBookingStatusRequest request, UserRequest userRequest) {
        BookingEntity bookingEntity = getBookingById(bookingId, null);

        UserResponse userResponse = userService.getUserInfo(userRequest, null);
        if ((userResponse.getRole().equals(RoleConstant.CUSTOMER_ROLE)
                        && !userResponse.getEmail().equals(bookingEntity.getCustomerEmail()))
                || (userResponse.getRole().equals(RoleConstant.HOTEL_MANAGER_ROLE)
                        && !userResponse.getEmail().equals(bookingEntity.getHotelOwnerEmail()))) {
            String message = "Admin or Owner Hotel/Customer can update booking.";
            log.error(message);
            throw new AppException(message, ErrorCode.UNAUTHORIZED);
        }

        if (request.getStatus() != null
                && !BookingStatus.nextValues(bookingEntity.getStatus(), true).contains(request.getStatus())) {
            String message = String.format(
                    "Cannot change status from %s to %s.", bookingEntity.getStatus(), request.getStatus());
            log.error(message);
            throw new AppException(message, ErrorCode.INVALID_KEY);
        }

        bookingEntity.setStatus(request.getStatus());
        save(bookingEntity);
    }

    @Override
    public BookingEntity updateBookingPaymentValue(String paymentId, Boolean isPaid, PaymentInfo paymentInfo) {
        BookingEntity bookingEntity = getBookingByPaymentId(paymentId);
        bookingEntity.setPaid(isPaid);
        bookingEntity.setPaymentInfo(paymentInfo);
        return save(bookingEntity);
    }

    @Override
    public BookingResponse createBooking(UserRequest userRequest, BookingRequest request) {

        List<BookingEntity> bookings = bookingRepository.findAllByHotelIdAndRoomIdAndCustomerEmail(
                request.getHotelId(), request.getRoomId(), userRequest.getEmail());
        if (bookings.stream()
                .anyMatch(b -> b.getStartDate().isAfter(DateTimeUtils.toLocalDate(request.getStartDate()))
                        && b.getEndDate().isBefore(DateTimeUtils.toLocalDate(request.getEndDate())))) {
            String message = "Booking already exists for the given date range";
            log.error(message);
            throw new AppException(message, ErrorCode.USER_NOT_EXISTED);
        }

        HotelEntity hotel = hotelService.getHotelById(request.getHotelId());
        if (hotel.getRooms().stream().noneMatch(r -> r.getId().equals(request.getRoomId()))) {
            String message = String.format("Room %s not available for the given hotel.", request.getRoomId());
            log.error(message);
            throw new AppException(message, ErrorCode.USER_EXISTED);
        }

        UserResponse userResponse = userService.getUserInfo(userRequest, null);

        BookingEntity bookingEntity = bookingMapper.toEntity(request);
        bookingEntity.setCustomerEmail(userResponse.getEmail());
        bookingEntity.setCustomerName(userResponse.getName());
        bookingEntity.setHotelOwnerEmail(hotel.getEmail());
        bookingEntity.setPaid(false);
        bookingEntity.setPaymentId(generatePaymentId(request.getPaymentChannel(), userResponse.getEmail()));
        bookingEntity.setTimeRules(hotel.getTimeRules());
        BookingEntity entity = save(bookingEntity);
        // save bookingId redis
        pendingBookingService.assignPending(entity.getId());
        return bookingMapper.toResponse(entity);
    }

    @Override
    public List<BookingResponse> listMyBookings(UserRequest userRequest) {
        UserResponse userResponse = userService.getUserInfo(userRequest, null);

        List<BookingEntity> bookings;
        switch (userResponse.getRole()) {
            case RoleConstant.HOTEL_MANAGER_ROLE -> bookings =
                    bookingRepository.findAllByHotelOwnerEmail(userResponse.getEmail());
            case RoleConstant.RECEPTIONIST_ROLE -> {
                HotelEntity hotel = hotelService.getReceptionistHotel(userResponse.getEmail());
                bookings = bookingRepository.findAllByHotelOwnerEmail(hotel.getEmail());
            }
            case RoleConstant.CUSTOMER_ROLE -> bookings =
                    bookingRepository.findAllByCustomerEmail(userResponse.getEmail());
            case RoleConstant.ADMIN_ROLE -> bookings = bookingRepository.findAll();
            default -> throw new AppException("Invalid user role", ErrorCode.FORBIDDEN_REQUEST);
        }

        return bookings.stream()
                .map(bookingMapper::toResponse)
                .sorted(Comparator.comparing(BookingResponse::getCreatedAt))
                .toList();
    }

    private String generatePaymentId(PaymentChannel paymentChannel, String customerEmail) {
        if (paymentChannel.equals(PaymentChannel.vn_pay)) {
            return String.format("%s_%s_%s", paymentChannel, StringUtils.getEmailName(customerEmail), new Date());
        } else {
            throw new AppException("Invalid payment channel", ErrorCode.INVALID_KEY);
        }
    }
}
