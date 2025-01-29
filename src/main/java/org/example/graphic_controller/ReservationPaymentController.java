package org.example.graphic_controller;

import javafx.application.HostServices;
import javafx.scene.layout.VBox;
import org.example.application_controller.BookRoom;
import org.example.bean.PaymentBean;
import org.example.view.ReservationPaymentView;

import java.io.IOException;

public class ReservationPaymentController {
    private final ReservationPaymentView reservationPaymentView;
    private final NavigationService navigationService; // Servizio di navigazione
    private static HostServices hostServices;

    public ReservationPaymentController(NavigationService navigationService, BookRoom bookRoom) {
        this.navigationService = navigationService;
        this.reservationPaymentView = new ReservationPaymentView();

        PaymentBean bean = bookRoom.getReservationBean();
        reservationPaymentView.setReservationData(bean);

        addEventHandlers();
    }

    private void addEventHandlers() {
        // Gestione del pagamento tramite bonifico
        reservationPaymentView.getBankTransferButton().setOnAction(e -> handleBankTransfer());

        // Gestione del pagamento tramite PayPal
        reservationPaymentView.getPaypalButton().setOnAction(e -> handlePaypalPayment());

        // Gestione della navigazione alla Home
        reservationPaymentView.getHomeButton().setOnAction(e -> navigateToHome());
    }

    private void handleBankTransfer() {
        // Mostra i dettagli per l'invio del bonifico
        System.out.println("Inserisci i dettagli del bonifico.");
    }

    private void handlePaypalPayment() {
        try {
            Runtime.getRuntime().exec("xdg-open https://www.paypal.com");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void navigateToHome() {
        navigationService.navigateToHomePage(navigationService);
    }

    public VBox getRoot() {
        return reservationPaymentView.getRoot();
    }

}
