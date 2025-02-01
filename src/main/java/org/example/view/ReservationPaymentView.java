package org.example.view;

import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import org.example.bean.PaymentBean;

public class ReservationPaymentView {
    private final VBox root;
    private final Label roomLabel;
    private final Label priceLabel;
    private final Label paymentDeadlineLabel;
    private final Label reason;
    private final Button homeButton;

    // Campi per il pagamento nell'app
    private final TextField cardNumberField;
    private final TextField cardHolderField;
    private final PasswordField cvvField;
    private final Text errorMessage;
    private final Button confirmPaymentButton;

    public ReservationPaymentView() {
        root = new VBox(15);
        root.setPrefSize(1280, 720);
        Label titleLabel = new Label("Prenotazione completata! Le è stata inviata un'email di conferma.");

        // Dati dinamici
        roomLabel = new Label();
        priceLabel = new Label();
        paymentDeadlineLabel = new Label();

        Label infoBankTransfer = new Label("Per fare tramite bonifico:");

        Label beneficiary = new Label("Beneficiario: Hotel Marittimo");

        Label iban = new Label("IBAN: IT60X0542811101000000123456");

        Label bicSwift = new Label("BIC/SWIFT: BPPIITRRXXX");

        reason = new Label("Causale per il bonifico: Prenotazione #");
        Label paymentService = new Label("Oppure paga adesso dall'applicazione");

        // Campi di input per il pagamento
        cardNumberField = new TextField();
        cardNumberField.setPromptText("Numero carta");

        cardHolderField = new TextField();
        cardHolderField.setPromptText("Intestatario carta");

        cvvField = new PasswordField();
        cvvField.setPromptText("CVV");

        errorMessage = new Text("Tutti i campi devono essere compilati");

        confirmPaymentButton = new Button("Conferma Pagamento");

        Label emailLabel = new Label("Anche i dati per effettuare il bonifico le sono stati mandati tramite email");
        homeButton = new Button("Torna alla Home");


        root.getChildren().addAll(titleLabel, roomLabel, priceLabel, paymentDeadlineLabel,
                infoBankTransfer, beneficiary, iban, bicSwift, reason, paymentService,
                cardNumberField, cardHolderField, cvvField, errorMessage,confirmPaymentButton, emailLabel, homeButton);
        errorMessage.setVisible(false);
    }

    public void setReservationData(PaymentBean paymentBean) {
        roomLabel.setText("Stanza assegnata: Stanza " + paymentBean.getRoomNumber());
        priceLabel.setText("Prezzo totale: " + paymentBean.getPrice());
        paymentDeadlineLabel.setText("Tempo per il pagamento: " + paymentBean.getPaymentDeadline());
        reason.setText("Causale per il bonifico: pagamento prenotazione hotel #" + paymentBean.getReservationID());
    }

    public VBox getRoot() {
        return root;
    }

    public Button getConfirmPaymentButton() {
        return confirmPaymentButton;
    }

    public Button getHomeButton() {
        return homeButton;
    }

    public String getCardNumber() {
        return cardNumberField.getText();
    }

    public String getCardHolder() {
        return cardHolderField.getText();
    }

    public String getCVV() {
        return cvvField.getText();
    }

    public Text getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String message) {
        errorMessage.setText(message);
        errorMessage.setVisible(true);
    }

    public void hideErrorMessage() {
        errorMessage.setVisible(false);
    }
}
