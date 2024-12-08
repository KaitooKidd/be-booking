package com.booking.notifications;

import java.util.Date;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;

import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import com.booking.auth.exception.SendEmailFailureException;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MailServiceImpl implements MailService {

    private final JavaMailSender mailSender;

    @Override
    public void sendVerificationEmail(String email, String content) {
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        try {
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);
            helper.setTo(email);
            helper.setSubject("Verification email");
            helper.setSentDate(new Date());
            helper.setText(content, true);
            mailSender.send(mimeMessage);
        } catch (MessagingException e) {
            throw new SendEmailFailureException(email);
        }
    }

    @Override
    public void sendCreatePassword(String email, String password) {
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        try {
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);
            helper.setTo(email);
            helper.setSubject("Account Information");
            helper.setSentDate(new Date());
            helper.setText(getPasswordEmailContent(password), true);
            mailSender.send(mimeMessage);
        } catch (MessagingException e) {
            throw new SendEmailFailureException(email);
        }
    }

    @Override
    public void sendResetPassword(String email, String password) {
        //        MimeMessage mimeMessage = mailSender.createMimeMessage();
        //        try {
        //            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);
        //            helper.setTo(email);
        //            helper.setSubject("UA Center reset password");
        //            helper.setSentDate(new Date());
        //            helper.setText(createResetPasswordContent(password), true);
        //            ClassPathResource classPathResource = new ClassPathResource("static/falcon-logo.png");
        //            helper.addInline("imageUrl", classPathResource);
        //            mailSender.send(mimeMessage);
        //        } catch (MessagingException e) {
        //            throw new SendEmailFailureException(email);
        //        }
    }

    private String createResetPasswordContent(String password) {
        return "<html>\n" + "\n"
                + "<body style=\"margin: 0 !important; padding: 0 !important;\">\n"
                + "  <div\n"
                + "    style=\"display: flex; justify-content: center; align-items: center; height: 100%; font-family: Helvetica, Arial, sans-serif;\">\n"
                + "    <div\n"
                + "      style=\"border-radius: 4px; border: 1px solid #d1d5db; max-width: 600px; margin: 0 auto 10px; box-shadow: 0 4px 6px -1px rgba(0, 0, 0, 0.08), 0 2px 4px -1px rgba(0, 0, 0, 0.02);\">\n"
                + "      <div style=\"display: flex; align-items: center; padding: 5px 30px; border-bottom: 1px solid #cbd5e1\">\n"
                + "        <img src=\"cid:imageUrl\" width=\"44px\" height=\"44px\" style=\"margin: auto 0;\"/>\n"
                + "        <h1 style=\"font-size: 20px; font-weight: 600; margin-left: 6px;color: black !important;\">Falcon Game Studio</h1>\n"
                + "      </div>\n"
                + "\n"
                + "      <div style=\"padding: 18px 30px;\">\n"
                + "        <div style=\"font-weight: 600; font-size: 20px; margin-bottom: 15px;color: black !important;\">Hi!</div>\n"
                + "        <div style=\"color: #27272a; font-size: 16px; font-weight: 400; line-height: 25px;\">\n"
                + "          <div>You are receiving this email because we received a password reset request for your account.</div><br>"
                + "            <div style=\"font-weight: 600; font-size: 20px; margin-bottom: 15px;color: black !important;\">Your new password: "
                + password + "  </div>\n" + "        </div>\n"
                + "      </div>\n"
                + "      <div\n"
                + "        style=\"background-color: #e5e5e5; border-top: 1px solid #d1d5db; text-align: center; color: #525252; padding: 14px 30px; font-size: 13px;\">\n"
                + "        <div>\n"
                + "          <span style=\"margin-right: 4px; opacity: 0.8;\">Send by Falcon Game Studio |</span>\n"
                + "          <span><a href=\"https://falcongames.com/\" style=\"text-decoration: none; color: #4f46e5 !important\">Terms and\n"
                + "              Conditions</a></span>\n"
                + "        </div>\n"
                + "        <div style=\"margin-top: 16px; font-size: 14px;\">Do not reply to this email.</div>\n"
                + "      </div>\n"
                + "    </div>\n"
                + "  </div>\n"
                + "</body>\n"
                + "\n"
                + "</html>";
    }

    private String getPasswordEmailContent(String password) {
        return "<!DOCTYPE html>\n" + "<html lang=\"en\">\n"
                + "  <head>\n"
                + "    <meta charset=\"UTF-8\" />\n"
                + "    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\" />\n"
                + "    <title>Account Password</title>\n"
                + "    <style>\n"
                + "      body {\n"
                + "        font-family: Arial, sans-serif;\n"
                + "        margin: 0;\n"
                + "        padding: 0;\n"
                + "        background-color: #f9f9f9;\n"
                + "        color: #333;\n"
                + "      }\n"
                + "      .container {\n"
                + "        max-width: 600px;\n"
                + "        margin: 20px auto;\n"
                + "        background: #ffffff;\n"
                + "        border-radius: 8px;\n"
                + "        box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);\n"
                + "        padding: 20px;\n"
                + "      }\n"
                + "      .header {\n"
                + "        text-align: center;\n"
                + "        background-color: #4caf50;\n"
                + "        color: white;\n"
                + "        padding: 10px 0;\n"
                + "        border-radius: 8px 8px 0 0;\n"
                + "      }\n"
                + "      .header h1 {\n"
                + "        margin: 0;\n"
                + "        font-size: 24px;\n"
                + "      }\n"
                + "      .content {\n"
                + "        padding: 20px;\n"
                + "      }\n"
                + "      .content p {\n"
                + "        font-size: 16px;\n"
                + "        line-height: 1.6;\n"
                + "      }\n"
                + "      .password {\n"
                + "        font-size: 20px;\n"
                + "        font-weight: bold;\n"
                + "        color: #4caf50;\n"
                + "      }\n"
                + "      .footer {\n"
                + "        text-align: center;\n"
                + "        margin-top: 20px;\n"
                + "        padding-top: 10px;\n"
                + "        border-top: 1px solid #ddd;\n"
                + "        font-size: 14px;\n"
                + "        color: #777;\n"
                + "      }\n"
                + "    </style>\n"
                + "  </head>\n"
                + "  <body>\n"
                + "    <div class=\"container\">\n"
                + "      <div class=\"header\">\n"
                + "        <h1>Account Information</h1>\n"
                + "      </div>\n"
                + "      <div class=\"content\">\n"
                + "        <p>Dear User,</p>\n"
                + "        <p>\n"
                + "          We are providing you with the password for your account. Please find\n"
                + "          your password below:\n"
                + "        </p>\n"
                + "        <p class=\"password\">"
                + password + "</p>\n" + "        <p>\n"
                + "          Please make sure to change your password after logging in for security\n"
                + "          reasons.\n"
                + "        </p>\n"
                + "        <p>\n"
                + "          If you did not request this email or if you have any concerns, please\n"
                + "          contact our support team immediately.\n"
                + "        </p>\n"
                + "      </div>\n"
                + "      <div class=\"footer\">\n"
                + "       <table cellpadding=\"0\" cellspacing=\"0\" class=\"m_8036448848028938201es-footer\" align=\"center\" role=\"none\" style=\"border-collapse:collapse;border-spacing:0;table-layout:fixed!important;width:100%;background-color:transparent;background-repeat:repeat;background-position:center top\"><tbody><tr><td align=\"center\" style=\"padding:0;Margin:0\"><table class=\"m_8036448848028938201es-footer-body\" align=\"center\" cellpadding=\"0\" cellspacing=\"0\" style=\"border-collapse:collapse;border-spacing:0;background-color:transparent;width:640px\" role=\"none\"><tbody><tr><td align=\"left\" style=\"Margin:0;padding-top:20px;padding-bottom:20px;padding-left:20px;padding-right:20px\"><table cellpadding=\"0\" cellspacing=\"0\" width=\"100%\" role=\"none\" style=\"border-collapse:collapse;border-spacing:0\"><tbody><tr><td align=\"left\" style=\"padding:0;Margin:0;width:600px\"><table cellpadding=\"0\" cellspacing=\"0\" width=\"100%\" role=\"presentation\" style=\"border-collapse:collapse;border-spacing:0\"><tbody><tr><td align=\"center\" style=\"padding:0;Margin:0;padding-top:15px;padding-bottom:15px;font-size:0\"><table cellpadding=\"0\" cellspacing=\"0\" class=\"m_8036448848028938201es-table-not-adapt m_8036448848028938201es-social\" role=\"presentation\" style=\"border-collapse:collapse;border-spacing:0\"><tbody><tr><td align=\"center\" valign=\"top\" style=\"padding:0;Margin:0;padding-right:30px\"><img title=\"Facebook\" src=\"https://ci3.googleusercontent.com/meips/ADKq_NahdpmH-nqb1np-kgipfRQS1yonbKi7jXWsx5Tzfai_iMjAuxlLX157kMSjjsAT99yFGU7qWCzIBrn3SuLZb6Iqcvd9mw-BEmjiyzt1As0HPd1Qd_T-2aTFEbUNm26CDeK2VO2XNOUdQogpe8iQkWqTldoNkwVNzQ=s0-d-e1-ft#https://eitehrg.stripocdn.email/content/assets/img/social-icons/logo-black/facebook-logo-black.png\" alt=\"Fb\" width=\"32\" height=\"32\" style=\"display:block;border:0;outline:0;text-decoration:none\" class=\"CToWUd\" data-bit=\"iit\"></td><td align=\"center\" valign=\"top\" style=\"padding:0;Margin:0;padding-right:30px\"><img title=\"Twitter\" src=\"https://ci3.googleusercontent.com/meips/ADKq_NbhWoHJp4iYMpdUvOPgI2-2MXqVV9FUTg9tNbvgUveQrWSzv0AUcEwitGXH4Q3Bjzf2Q4EAxlpVnR3mGj_T-yEq-ZPrULKMn_oKBOZ2crFLTzSI3DpX9moFhiNQmA3m4cUflVd4x-Xzi6PS7r0imQUfYfB-T39r=s0-d-e1-ft#https://eitehrg.stripocdn.email/content/assets/img/social-icons/logo-black/twitter-logo-black.png\" alt=\"Tw\" width=\"32\" height=\"32\" style=\"display:block;border:0;outline:0;text-decoration:none\" class=\"CToWUd\" data-bit=\"iit\"></td><td align=\"center\" valign=\"top\" style=\"padding:0;Margin:0;padding-right:30px\"><img title=\"Instagram\" src=\"https://ci3.googleusercontent.com/meips/ADKq_NYSa71SsPw7GjDnVr1kwhdkppyU3uOtkxXlg9BBC0GVv0hQvHroqdsNc2tse2pGtRabe2uCMcqYW1dFcUhcIYE3Xw0l8vZysTMP6Z4RdSuIsSeg8_wMeuUGDvctYxtbKF_bGUYu74pNShshdcHGev9BmhOulu5JpRo=s0-d-e1-ft#https://eitehrg.stripocdn.email/content/assets/img/social-icons/logo-black/instagram-logo-black.png\" alt=\"Inst\" width=\"32\" height=\"32\" style=\"display:block;border:0;outline:0;text-decoration:none\" class=\"CToWUd\" data-bit=\"iit\"></td><td align=\"center\" valign=\"top\" style=\"padding:0;Margin:0\"><img title=\"Youtube\" src=\"https://ci3.googleusercontent.com/meips/ADKq_Nayu3xfUcARls9LTbZrWnFpdoD6N-dl3u3-7VwWIjIICIruC-cu2AMnjbEOZSQiFf33OmTmlB4Yp8tSgDTsMB6VUMCpZqfyK0C7eKJ6s4rj52sxamk6jsdJ79l_pN_31d37RM8kGv2_-z4FBZAG_ReSBzNZgg0l=s0-d-e1-ft#https://eitehrg.stripocdn.email/content/assets/img/social-icons/logo-black/youtube-logo-black.png\" alt=\"Yt\" width=\"32\" height=\"32\" style=\"display:block;border:0;outline:0;text-decoration:none\" class=\"CToWUd\" data-bit=\"iit\"></td></tr></tbody></table></td></tr><tr><td align=\"center\" style=\"padding:0;Margin:0;padding-bottom:35px\"><p>PTIT Booking © 2024&nbsp;&nbsp;All Rights Reserved.</p><p>Cau Giay, Ha Noi, Vietnam</p></td></tr></tbody></table></td></tr></tbody></table></td></tr></tbody></table></td></tr></tbody></table>\n"
                + "      </div>\n"
                + "    </div>\n"
                + "  </body>\n"
                + "</html>\n";
    }
}
