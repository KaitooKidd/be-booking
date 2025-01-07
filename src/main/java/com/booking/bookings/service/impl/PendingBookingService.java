package com.booking.bookings.service.impl;

import java.time.Duration;
import java.util.concurrent.atomic.AtomicBoolean;

import org.redisson.api.RMap;
import org.redisson.api.RedissonClient;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.booking.bookings.repository.BookingRepository;

import lombok.extern.slf4j.Slf4j;

@Component
@EnableScheduling
@Slf4j
public class PendingBookingService {
    private static final String REDIS_PREFIX = "pending_booked";
    private final BookingRepository bookingRepository;

    private final RMap<String, Long> redissonMap;

    public PendingBookingService(RedissonClient redissonClient, BookingRepository bookingRepository) {
        this.bookingRepository = bookingRepository;
        redissonMap = redissonClient.getMap(REDIS_PREFIX);
    }

    public Iterable<String> waitingBookIds() {
        return redissonMap.keySet();
    }

    public boolean tryExpires(String bookId) {
        AtomicBoolean result = new AtomicBoolean(false);
        redissonMap.compute(bookId, (k, timeMillis) -> {
            if (timeMillis == null) {
                log.info("Request of this key has been processed successfully, continuing!!!");
                return null;
            }
            // case: còn < 60s ng dùng thanh toán
            long ttl = System.currentTimeMillis()
                    - timeMillis
                    - Duration.ofMinutes(16).toMillis();

            if (ttl > 0) {
                bookingRepository.deleteById(k);
                System.out.println("Removed expired booking with ID: " + k);
                result.set(true);
                return null;
            }
            return timeMillis;
        });
        return result.get();
    }

    public void assignPending(String bookingId) {
        redissonMap.putIfAbsent(bookingId, System.currentTimeMillis());
    }

    public boolean tryPayPending(String bookingId) {
        return redissonMap.remove(bookingId) != null;
    }

    public Long remainTime(String bookingId) {
        Long timeMillis = redissonMap.get(bookingId);
        if (timeMillis == null) {
            return null;
        }
        return System.currentTimeMillis() - timeMillis - Duration.ofMinutes(15).toMillis();
    }

    @Scheduled(fixedRate = 60000)
    public void checkPendingBookings() {
        for (String waitingBookId : waitingBookIds()) {
            if (tryExpires(waitingBookId)) {
                log.warn(String.format("Booking id %s is expired", waitingBookId));
            }
        }
    }
}
