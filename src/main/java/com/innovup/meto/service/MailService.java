package com.innovup.meto.service;

import com.innovup.meto.entity.ConfirmationToken;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MailService {

    private final JavaMailSender javaMailSender;

    @Async
    public void send(ConfirmationToken confirmation) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(confirmation.getUser().getEmail());
        message.setSubject("Medical Tourism Account Verification");
        message.setText(
                "To confirm your account, please click here: http://localhost:8800/register/confirm-account?token=" + confirmation.getToken()
        );
        javaMailSender.send(message);
    }
}