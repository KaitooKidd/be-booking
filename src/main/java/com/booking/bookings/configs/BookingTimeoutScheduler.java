package com.booking.bookings.configs;

import java.util.Set;

import org.redisson.api.RBucket;
import org.redisson.api.RKeys;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.booking.bookings.repository.BookingRepository;

@Component
@EnableScheduling
public class BookingTimeoutScheduler {

    @Autowired
    private RedissonClient redissonClient;

    @Autowired
    private BookingRepository bookingRepository;

    private static final String REDIS_PREFIX = "pending_booked:";

    @Scheduled(fixedDelay = 60000)
    public void checkPendingBookings() {
        RKeys keys = redissonClient.getKeys();
        Iterable<String> redisKeys = keys.getKeysByPattern(REDIS_PREFIX + "*");

        if (redisKeys == null) {
            return;
        }

        for (String key : redisKeys) {
            RBucket<String> bucket = redissonClient.getBucket(key);
            long ttl = bucket.remainTimeToLive();

            if (ttl > 0 && ttl <= 15 * 1000) {
                String bookingId = bucket.get();

                if (bookingId != null) {
                    bookingRepository.deleteById(bookingId);
                    System.out.println("Removed expired booking with ID: " + bookingId);
                }

                bucket.delete();
            }
        }
    }
}
