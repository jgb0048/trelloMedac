package com.medac.trello.api.recovery;

import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;
import java.util.concurrent.ConcurrentHashMap;
import java.util.UUID;
import java.util.Map;

@RestController
@RequestMapping("/api/recovery")
public class PasswordRecoveryController {

    private static final int TOKEN_EXPIRATION_MINUTES = 15;

    // Simulación de base de datos
    private final Map<String, String> userPasswords = new ConcurrentHashMap<>();
    private final Map<String, TokenData> resetTokens = new ConcurrentHashMap<>();

    private final EmailService emailService;

    @Autowired
    public PasswordRecoveryController(EmailService emailService) {
        this.emailService = emailService;
        // Usuario de prueba
        userPasswords.put("usuario@correo.com", "1234");
    }

    // 1️⃣ Solicitar recuperación de contraseña
    @PostMapping("/request")
    public ResponseEntity<String> requestRecovery(@RequestParam String email) {
        if (!userPasswords.containsKey(email)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("No existe un usuario con ese correo.");
        }

        String token = UUID.randomUUID().toString();
        LocalDateTime expiryTime = LocalDateTime.now().plusMinutes(TOKEN_EXPIRATION_MINUTES);
        resetTokens.put(token, new TokenData(email, expiryTime));

        try {
            emailService.sendRecoveryEmail(email, token);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error al enviar el correo de recuperación: " + e.getMessage());
        }

        return ResponseEntity.ok("Se ha enviado un correo con las instrucciones para recuperar la contraseña.");
    }

    // 2️⃣ Restablecer contraseña usando el token
    @PostMapping("/reset")
    public ResponseEntity<String> resetPassword(
            @RequestParam String token,
            @RequestParam String newPassword) {

        TokenData tokenData = resetTokens.get(token);

        if (tokenData == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Token inválido o no encontrado.");
        }

        if (tokenData.getExpiryTime().isBefore(LocalDateTime.now())) {
            resetTokens.remove(token);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("El token ha expirado. Solicita uno nuevo.");
        }

        String email = tokenData.getEmail();
        userPasswords.put(email, newPassword);
        resetTokens.remove(token);

        return ResponseEntity.ok("Contraseña actualizada correctamente para " + email + ".");
    }

    // Clase interna para guardar datos del token
    private static class TokenData {
        private final String email;
        private final LocalDateTime expiryTime;

        public TokenData(String email, LocalDateTime expiryTime) {
            this.email = email;
            this.expiryTime = expiryTime;
        }

        public String getEmail() {
            return email;
        }

        public LocalDateTime getExpiryTime() {
            return expiryTime;
        }
    }
}
