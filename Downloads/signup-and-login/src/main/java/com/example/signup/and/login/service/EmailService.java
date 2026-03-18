package com.example.signup.and.login.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmailService {

    private final JavaMailSender mailSender;

    @Value("${app.frontend.url}")
    private String frontendUrl;

    public void sendPasswordResetEmail(String to, String token) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject("Password Reset Request");
        message.setText(
                "Click the link below to reset your password:\n\n"
                        + frontendUrl + "/reset-password?token=" + token
                        + "\n\nThis link expires in 15 minutes."
                        + "\n\nIf you did not request this, ignore this email."
        );
        mailSender.send(message);

        System.out.println("Sending email to: " + to); // ← add this
        System.out.println("Frontend URL: " + frontendUrl); // ← add this
        // rest of your code
    }
    }







