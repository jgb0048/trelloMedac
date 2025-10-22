package com.medac.trello.api.recovery;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    private final JavaMailSender mailSender;

    @Autowired
    public EmailService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    public void sendRecoveryEmail(String to, String token) {
        String subject = "Recuperación de contraseña - Trello Clone";
        String message = "Hola,\n\n" +
                         "Has solicitado restablecer tu contraseña.\n\n" +
                         "Tu token de recuperación es:\n" +
                         token + "\n\n" +
                         "Este token expirará en 15 minutos.\n\n" +
                         "Si no solicitaste este cambio, puedes ignorar este mensaje.";

        SimpleMailMessage email = new SimpleMailMessage();
        email.setTo(to);
        email.setSubject(subject);
        email.setText(message);

        mailSender.send(email);
    }
}
