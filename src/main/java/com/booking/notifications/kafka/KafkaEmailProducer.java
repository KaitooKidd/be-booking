package com.booking.notifications.kafka;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import com.booking.utils.JsonUtils;

@Service
public class KafkaEmailProducer {

    private static final String TOPIC = "send-email-topic";

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    public void sendVerificationEmail(String recipientEmail, String content) {
        Map<String, String> emailEvent = Map.of(
                "recipientEmail", recipientEmail,
                "content", content);

        String message = JsonUtils.toString(emailEvent);
        kafkaTemplate.send(TOPIC, message);
    }

    public void sendCreatePassword(String email, String password) {
        Map<String, String> emailEvent = Map.of(
                "email", email,
                "pass", password);
        String message = JsonUtils.toString(emailEvent);
        kafkaTemplate.send(TOPIC, message);
    }
}
