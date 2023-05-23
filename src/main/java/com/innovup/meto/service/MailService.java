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
        message.setSubject("Tourisme Medicale Verification de Compte");
        message.setText(
                "pour confirmer votre compte, cliquez sur ce lien: http://localhost:8800/register/confirm-account?token=" + confirmation.getToken()
        );
        javaMailSender.send(message);
    }
}