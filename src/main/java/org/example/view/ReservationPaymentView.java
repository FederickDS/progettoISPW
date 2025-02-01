package org.example.view;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import org.example.bean.PaymentBean;

public class ReservationPaymentView {
    private final VBox root;
    private final Label roomLabel;
    private final Label priceLabel;
    private final Label paymentDeadlineLabel;
    private final Button bankTransferButton;
    private final Button paymentService;
    private final Label homeLabel;
    private final Button homeButton; // Nuovo bottone per tornare alla home

    public ReservationPaymentView() {
        root = new VBox(15);
        root.setPrefSize(1280, 720);
        Label titleLabel = new Label("Prenotazione completata! Le è stata inviata un'email di conferma.");
        // Dati da passare dinamicamente
        roomLabel = new Label();
        priceLabel = new Label();
        paymentDeadlineLabel = new Label();

        bankTransferButton = new Button("Invia Bonifico");
        paymentService = new Button("Paga dall'applicazione");
        homeLabel = new Label("Può sempre vedere i dati per il pagamento dalla sua area riservata");
        homeButton = new Button("Torna alla Home"); // Pulsante per la home

        root.getChildren().addAll(titleLabel,roomLabel, priceLabel, paymentDeadlineLabel, bankTransferButton, paymentService, homeLabel, homeButton);
    }

    public void setReservationData(PaymentBean paymentBean) {
        // imposta campi di reservation che vede l'utente
        Integer room = paymentBean.getRoomNumber();
        roomLabel.setText("Stanza assegnata: Stanza" + room.toString());
        priceLabel.setText("Prezzo totale: " + paymentBean.getPrice());
        paymentDeadlineLabel.setText("Tempo per il pagamento: " + paymentBean.getPaymentDeadline());
    }

    public VBox getRoot() {
        return root;
    }

    public Button getBankTransferButton() {
        return bankTransferButton;
    }

    public Button getPaymentService() {
        return paymentService;
    }

    public Button getHomeButton() { // Getter per il nuovo bottone
        return homeButton;
    }
}
