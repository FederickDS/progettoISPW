package org.example.graphic_controller;

import javafx.scene.layout.VBox;
import org.example.application_controller.BookRoom;
import org.example.bean.PaymentBean;
import org.example.facade.ApplicationFacade;
import org.example.view.ReservationPaymentView;

import java.util.logging.Logger;

public class ReservationPaymentController {
    private final ReservationPaymentView reservationPaymentView;
    private final NavigationService navigationService; // Servizio di navigazione
    private static boolean payment = false;
    private final PaymentBean bean;
    private static final Logger logger = Logger.getLogger(ReservationPaymentController.class.getName());

    public ReservationPaymentController(NavigationService navigationService, BookRoom bookRoom) {
        this.navigationService = navigationService;
        this.reservationPaymentView = new ReservationPaymentView();

        this.bean = bookRoom.getReservationBean();
        reservationPaymentView.setReservationData(bean);

        addEventHandlers();
    }

    private void addEventHandlers() {
        reservationPaymentView.getConfirmPaymentButton().setOnAction(e -> handleInAppPayment());
        reservationPaymentView.getHomeButton().setOnAction(e -> navigateToHome());
    }

    private void handleInAppPayment() {
        if(payment) {
            reservationPaymentView.setErrorMessage("Il pagamento è già andato a buon fine.");
            return;
        }
        String cardNumber = reservationPaymentView.getCardNumber();
        String cardHolder = reservationPaymentView.getCardHolder();
        String cvv = reservationPaymentView.getCVV();

        if (cardNumber.isEmpty() || cardHolder.isEmpty() || cvv.isEmpty()) {
            reservationPaymentView.setErrorMessage("Errore: tutti i campi devono essere compilati.");
            return;
        }

        boolean success = ApplicationFacade.processPayment(cardNumber, cvv, bean.getPrice()); // Simulazione pagamento
        if (success) {
            reservationPaymentView.setErrorMessage("Pagamento avvenuto con successo!");
            ApplicationFacade.sendGenericEmail("cliente@email.com", "Ricevuta Pagamento",
                    "Grazie per il pagamento! La transazione è stata completata.");
        } else {
            reservationPaymentView.setErrorMessage("Pagamento fallito");
        }
    }

    private void navigateToHome() {
        navigationService.navigateToHomePage(navigationService);
    }

    public VBox getRoot() {
        return reservationPaymentView.getRoot();
    }

}
