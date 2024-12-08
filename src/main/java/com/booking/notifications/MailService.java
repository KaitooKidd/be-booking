package com.booking.notifications;

public interface MailService {

    void sendVerificationEmail(String email, String content);

    void sendCreatePassword(String email, String content);

    void sendResetPassword(String email, String password);
}
