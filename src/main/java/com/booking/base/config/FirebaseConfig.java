package com.booking.base.config;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.nimbusds.jwt.SignedJWT;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtException;
import org.springframework.stereotype.Component;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.ParseException;
import java.util.List;

@Configuration
public class FirebaseConfig {
    @Value("${firebase.credential}")
    private String credential;

    @Bean
    public FirebaseApp firebaseAdmin() throws IOException {
        FirebaseApp firebaseApp = null;
        List<FirebaseApp> firebaseApps = FirebaseApp.getApps();
        if (firebaseApps != null && !firebaseApps.isEmpty()) {
            for (FirebaseApp app : firebaseApps) {
                if (app.getName().equals(FirebaseApp.DEFAULT_APP_NAME))
                    firebaseApp = app;
            }
        }

        if (firebaseApp == null) {
            GoogleCredentials credentials = GoogleCredentials.fromStream(new FileInputStream(credential));
            FirebaseOptions options = new FirebaseOptions.Builder()
                    .setCredentials(credentials)
                    .build();
            firebaseApp = FirebaseApp.initializeApp(options);
        }

        return firebaseApp;
    }
}