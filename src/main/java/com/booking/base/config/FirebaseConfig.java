package com.booking.base.config;

import java.io.FileInputStream;
import java.io.IOException;

import jakarta.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.auth.FirebaseAuth;

import lombok.extern.log4j.Log4j2;

@Configuration
@Log4j2
public class FirebaseConfig {
    @Value("${firebase.credential}")
    private String credential;

    @PostConstruct
    public void initialize() throws IOException {
        GoogleCredentials credentials = GoogleCredentials.fromStream(new FileInputStream(credential));
        FirebaseOptions options =
                new FirebaseOptions.Builder().setCredentials(credentials).build();
        FirebaseApp.initializeApp(options);
        log.info("Firebase initialized");
    }

    @Bean
    public FirebaseAuth firebaseAuth() {
        return FirebaseAuth.getInstance();
    }
}
