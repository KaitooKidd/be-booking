package com.booking.notifications.kafka;

import java.util.Map;

import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import com.booking.notifications.MailService;
import com.booking.utils.JsonUtils;
import com.booking.utils.StringUtils;

import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Service
@EnableKafka
@AllArgsConstructor
@Log4j2
public class KafkaEmailConsumer {

    private final MailService mailService;

    @KafkaListener(topics = "send-email-topic", groupId = "${KAFKA_CONSUMER_GROUP_ID}")
    public void consumeVerificationEmail(String message) {
        try {
            Map<String, String> emailEvent = JsonUtils.fromJson(message, Map.class);

            if (emailEvent == null || emailEvent.isEmpty()) {
                log.warn("Received empty or null email event: {}", message);
                return;
            }

            if (emailEvent.containsKey("recipientEmail")) {
                handleVerificationEmailEvent(emailEvent);
            } else {
                handleCreatePasswordEvent(emailEvent);
            }
        } catch (Exception e) {
            log.error("Error while consuming email event: {}", e.getMessage());
        }
    }

    private void handleVerificationEmailEvent(Map<String, String> emailEvent) {
        String recipientEmail = emailEvent.get("recipientEmail");
        String emailContent = emailEvent.get("content");

        if (StringUtils.isEmpty(recipientEmail) || StringUtils.isEmpty(emailContent)) {
            log.warn("Invalid verification email event: {}", emailEvent);
            return;
        }

        try {
            mailService.sendVerificationEmail(recipientEmail, emailContent);
            log.info("Verification email sent to {}", recipientEmail);
        } catch (Exception e) {
            log.error("Error sending verification email to {}: {}", recipientEmail, e.getMessage(), e);
        }
    }

    private void handleCreatePasswordEvent(Map<String, String> emailEvent) {
        String email = emailEvent.get("email");
        String pass = emailEvent.get("pass");

        if (StringUtils.isEmpty(email) || StringUtils.isEmpty(pass)) {
            log.warn("Invalid create password email event: {}", emailEvent);
            return;
        }

        try {
            mailService.sendCreatePassword(email, pass);
            log.info("Password email sent to {}", email);
        } catch (Exception e) {
            log.error("Error sending password email to {}: {}", email, e.getMessage(), e);
        }
    }
}
