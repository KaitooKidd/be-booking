package com.booking.bookings.configs;

import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class RedisConfiguration {

    @Value("${redis.host}")
    private String redisHost;

    @Value("${redis.port}")
    private String redisPort;

    @Value("${redis.password}")
    private String redisPassword;

    @Value("${redis.database}")
    private int redisDatabase;

    private RedissonClient redisClient;

    @Bean
    public RedissonClient redisConfig() {
        if (redisClient == null) {
            Config redissonConfig = new Config();
            redissonConfig
                    .useSingleServer()
                    .setIdleConnectionTimeout(10000)
                    .setConnectionMinimumIdleSize(10)
                    .setConnectionPoolSize(32)
                    .setAddress("redis://".concat(redisHost).concat(":").concat(redisPort))
                    .setPassword(redisPassword)
                    .setDatabase(redisDatabase);
            redisClient = Redisson.create(redissonConfig);
        }
        return redisClient;
    }
}
