package com.example.quiz.auth.service;

import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.util.concurrent.Future;

@Service
@Log
public class EmailService {

    @Value("${host.url}")
    private String host;

    @Value("${email.from}")
    private String emailFrom;

    private JavaMailSender sender;

    @Autowired
    public EmailService(JavaMailSender sender) {
        this.sender = sender;
    }

    @Async
    public Future<Boolean> sendActivationEmail(String email, String username, String uuid) {
        try {
            MimeMessage mimeMessage = sender.createMimeMessage();
            MimeMessageHelper message = new MimeMessageHelper(mimeMessage, "utf-8");

            String url = host + "/activate-account/" + uuid;

            String htmlMsg = String.format(
                    "Hi.<br/><br/>" +
                            "You have created an account for the web application \"Quiz\": %s <br/><br/>" +
                            "<a href='%s'>%s</a><br/><br/>",  username, url, "To confirm registration, click on this link");

            mimeMessage.setContent(htmlMsg, "text/html");

            message.setTo(email);
            message.setFrom(emailFrom);
            message.setSubject("Account activation is required");
            message.setText(htmlMsg, true);
            sender.send(mimeMessage);

            return new AsyncResult<>(true);

        } catch (MessagingException e) {
            e.printStackTrace();
        }

        return new AsyncResult<>(false);

    }

    @Async
    public Future<Boolean> sendResetPasswordEmail(String email, String token) {
        try {
            MimeMessage mimeMessage = sender.createMimeMessage();
            MimeMessageHelper message = new MimeMessageHelper(mimeMessage, "utf-8");

            String url = host + "/update-password/" + token;

            String htmlMsg = String.format(
                    "Hi.<br/><br/>" +
                            "Someone requested a password reset for a web application \"Quiz\".<br/><br/>" +
                            "If it wasn't you, ignore this email.<br/><br/> Click on the link below if you want to reset your password: <br/><br/> " +
                            "<a href='%s'>%s</a><br/><br/>", url, "Reset your password");

            mimeMessage.setContent(htmlMsg, "text/html");

            message.setTo(email);
            message.setSubject("Reset your password");
            message.setFrom(emailFrom);
            message.setText(htmlMsg, true);
            sender.send(mimeMessage);

            return new AsyncResult<>(true);

        } catch (MessagingException e) {
            e.printStackTrace();
        }
        return new AsyncResult<>(false);
    }


}
