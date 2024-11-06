package com.booking.base.config;

import com.booking.users.constant.RoleConstant;
import com.booking.users.dtos.request.UserCreationRequest;
import com.booking.users.entity.RoleEntity;
import com.booking.users.repository.RoleRepository;
import com.booking.users.service.UserService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.experimental.NonFinal;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class ApplicationInitConfig {

    @NonFinal
    static final String ADMIN_EMAIL = "admin@gmail.com";

    @NonFinal
    static final String ADMIN_PASSWORD = "admin123";

    @Bean
    @ConditionalOnProperty(
            prefix = "spring",
            value = "datasource.driverClassName",
            havingValue = "com.mysql.cj.jdbc.Driver")
    ApplicationRunner applicationRunner(UserService userService, RoleRepository roleRepository) {
        log.info("Initializing application.....");
        return args -> {
            if (userService.getUserByEmail(ADMIN_EMAIL, null) == null) {
                roleRepository.save(RoleEntity.builder()
                        .name(RoleConstant.CUSTOMER_ROLE)
                        .description("UserEntity role")
                        .build());

                roleRepository.save(RoleEntity.builder()
                        .name(RoleConstant.HOTEL_MANAGER_ROLE)
                        .description("Hotel Manager role")
                        .build());

                roleRepository.save(RoleEntity.builder()
                        .name(RoleConstant.RECEPTIONIST_ROLE)
                        .description("Receptionist role")
                        .build());

                roleRepository.save(RoleEntity.builder()
                        .name(RoleConstant.ADMIN_ROLE)
                        .description("Admin role")
                        .build());

                userService.createUser(UserCreationRequest.builder()
                        .email(ADMIN_EMAIL)
                        .password(ADMIN_PASSWORD)
                        .roleName(RoleConstant.ADMIN_ROLE)
                        .isVerified(true)
                        .shouldCreateFirebaseUser(true)
                        .build());

                log.warn("admin user has been created with default password: admin, please change it");
            }
            log.info("Application initialization completed .....");
        };
    }
}
