package org.example.bean;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class TestPaymentBean {

    private PaymentBean paymentBean;

    @BeforeEach
    void setUp() {
        paymentBean = new PaymentBean(101, "100.50", "2025-02-10", 12345);
    }

    @Test
    void testValidPayment() {
        // Simuliamo un pagamento valido
        boolean result = processPayment("4111111111111111", 100.50);
        assertTrue(result, "Il pagamento valido dovrebbe essere accettato.");
    }

    @Test
    void testInvalidCardNumber() {
        // Simuliamo un numero di carta errato
        boolean result = processPayment("1234567890123456", 100.50);
        assertFalse(result, "Un numero di carta non valido dovrebbe essere rifiutato.");
    }

    @Test
    void testInsufficientFunds() {
        // Simuliamo un pagamento con fondi insufficienti
        boolean result = processPayment("4111111111111111", 1000.00);
        assertFalse(result, "Un pagamento con fondi insufficienti dovrebbe essere rifiutato.");
    }

    // Metodo di simulazione del pagamento (mocking di un sistema di pagamento)
    private boolean processPayment(String cardNumber, double amount) {
        if (!cardNumber.matches("\\d{16}") || !cardNumber.startsWith("4")) {
            return false; // Numero di carta non valido
        }

        double accountBalance = 500.00; // Simuliamo un saldo disponibile
        return amount <= accountBalance; // Se l'importo è coperto dal saldo, accettiamo il pagamento
    }
}
