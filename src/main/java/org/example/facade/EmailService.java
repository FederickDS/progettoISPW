package org.example.facade;

import java.util.logging.Logger;

public class EmailService {
    private static final Logger logger = Logger.getLogger(EmailService.class.getName());

    /**
     * Simula l'invio di un'email.
     * @param toEmail Indirizzo email del destinatario.
     * @param subject Oggetto dell'email.
     * @param body Contenuto dell'email.
     */
    public static void sendEmail(String toEmail, String subject, String body) {
        logger.info("Simulazione invio email...");
        logger.info("Destinatario: " + toEmail);
        logger.info("Oggetto: " + subject);
        logger.info("Contenuto:" + body);
        logger.info("Email inviata con successo (simulato).");
    }
}
