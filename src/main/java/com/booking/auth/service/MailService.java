package com.booking.auth.service;

public interface MailService {
    /**
     * Send an email active new user
     *
     * @param email email of new user
     * @param organizationCode organizationCode of new user
     * @param googleAuthenticatorSecretKey secret key
     */
    void sendActiveUserMail(String email, String organizationCode, String googleAuthenticatorSecretKey);

    void sendVerificationEmail(String email, String content);

    /**
     * Send an email active new user
     *
     * @param email email of new user
     * @param password new password
     */
    void sendResetPassword(String email, String password);

    /**
     * Send an email active new user
     *
     * @param email email of user
     * @param googleAuthenticatorSecretKey secret key
     */
    void sendQRCode(String email, String googleAuthenticatorSecretKey);
}
