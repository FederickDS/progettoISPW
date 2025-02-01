package org.example.facade;

import java.util.logging.Logger;

public class PaymentService {
    private static final Logger logger = Logger.getLogger(PaymentService.class.getName());

    /**
     * Simula un pagamento e restituisce un esito positivo o negativo.
     * @param cardNumber Numero della carta di credito.
     * @param amount Importo da pagare.
     * @param currency Valuta (es. EUR, USD).
     * @return `true` se il pagamento ha avuto successo, `false` se fallisce.
     */
    public static boolean processPayment(String cardNumber, String amount, String currency, String CVV) {
        logger.info("Simulazione pagamento...");
        logger.info("Numero carta: **** **** **** " + cardNumber.substring(cardNumber.length() - 4));
        logger.info("Controllo data di scadenza..." + CVV);
        logger.info("Importo: " + amount + " " + currency);

        // Simuliamo una probabilità di successo del 90%
        return Math.random() < 0.9;
    }
}
