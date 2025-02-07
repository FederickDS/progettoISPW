package org.example.graphic_controller;

import javafx.scene.layout.VBox;
import javafx.scene.control.RadioButton;
import org.example.application_controller.BookRoom;
import org.example.bean.PaymentBean;
import org.example.facade.ApplicationFacade;
import org.example.view.ReservationPaymentViewAlternative;

public class ReservationPaymentControllerAlternative {
    private final ReservationPaymentViewAlternative reservationPaymentView;
    private final NavigationService navigationService;
    private boolean payment = false;
    private final BookRoom bookRoom;
    private final PaymentBean bean;

    public ReservationPaymentControllerAlternative(NavigationService navigationService, BookRoom bookRoom) {
        this.navigationService = navigationService;
        this.reservationPaymentView = new ReservationPaymentViewAlternative();
        this.bean = bookRoom.getReservationBean();
        this.bookRoom = bookRoom;
        reservationPaymentView.setReservationData(bean);

        addEventHandlers();
    }

    private void addEventHandlers() {
        reservationPaymentView.getConfirmButton().setOnAction(e -> handlePayment());
        reservationPaymentView.getHomeButton().setOnAction(e->navigateToHome());
    }

    private void handlePayment() {
        if (payment) {
            reservationPaymentView.setErrorMessage("Il pagamento è già andato a buon fine.");
            return;
        }

        RadioButton selectedOption = (RadioButton) reservationPaymentView.getRoot().lookup(".radio-button:selected");
        if (selectedOption == null) {
            reservationPaymentView.setErrorMessage("Selezionare un metodo di pagamento.");
            return;
        }

        if ("Pagamento con Carta".equals(selectedOption.getText())) {
            String cardNumber = reservationPaymentView.getCardNumber();
            String cardHolder = reservationPaymentView.getCardHolder();
            String cvv = reservationPaymentView.getCVV();

            if (cardNumber.isEmpty() || cardHolder.isEmpty() || cvv.isEmpty()) {
                reservationPaymentView.setErrorMessage("Errore: tutti i campi devono essere compilati.");
                return;
            }

            boolean success = ApplicationFacade.processPayment(cardNumber, cvv, bean.getPrice());
            if (success) {
                finalizePayment();
            } else {
                reservationPaymentView.setErrorMessage("Pagamento fallito");
            }
        } else {
            finalizePayment();
        }
    }

    private void finalizePayment() {
        bookRoom.confirmReservation(bean.getReservationID());
        reservationPaymentView.setErrorMessage("Pagamento avvenuto con successo!");
        ApplicationFacade.sendGenericEmail("cliente@email.com", "Ricevuta Pagamento", "Grazie per il pagamento! La transazione è stata completata.");
        payment = true;
    }

    private void navigateToHome() {
        navigationService.navigateToHomePage(navigationService);
    }

    public VBox getRoot() {
        return reservationPaymentView.getRoot();
    }
}
