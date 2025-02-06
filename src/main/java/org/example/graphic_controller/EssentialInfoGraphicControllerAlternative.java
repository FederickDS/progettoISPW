package org.example.graphic_controller;

import javafx.scene.layout.VBox;
import org.example.application_controller.EssentialInfoController;
import org.example.entity.Client;
import org.example.facade.ApplicationFacade;
import org.example.view.EssentialInfoViewAlternative;

import java.util.regex.Pattern;

public class EssentialInfoGraphicControllerAlternative {
    private final EssentialInfoViewAlternative essentialInfoView;
    private final String previousPage;
    private final String nextPage;
    private final NavigationService navigationService;
    private static final String EMAIL_REGEX = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$";

    public EssentialInfoGraphicControllerAlternative(NavigationService navigationService, String previousPage, String nextPage) {
        this.navigationService = navigationService;
        this.previousPage = previousPage;
        this.nextPage = nextPage;
        this.essentialInfoView = new EssentialInfoViewAlternative();
        addEventHandlers();
    }

    private void addEventHandlers() {
        essentialInfoView.getConfirmButton().setOnAction(e -> handleRegistration());
        essentialInfoView.getBackButton().setOnAction(e -> this.navigationService.navigateBack(previousPage, this.navigationService));
    }

    private void handleRegistration() {
        EssentialInfoController appController = new EssentialInfoController();

        // Controllo dei campi prima di procedere
        if (!checkFields(essentialInfoView)) {
            return;
        }
        String firstName = essentialInfoView.getFirstNameField().getText();
        String lastName = essentialInfoView.getLastNameField().getText();
        String phoneNumber = essentialInfoView.getPhoneNumberField().getText();
        String email = essentialInfoView.getEmailField().getText();


        Client client = appController.createClientFromInput(firstName, lastName, phoneNumber, email);
        String result = appController.registerClient(client);

        switch (result) {
            case "success":
                SessionManager.getInstance().setCredentials(
                        essentialInfoView.getEmailField().getText(),
                        null,
                        essentialInfoView.getSelectedInfoType()
                );
                navigateToNextPage();
                break;

            case "error:client_exists":
                essentialInfoView.getEmailError().setText("Questo indirizzo email è già registrato.");
                essentialInfoView.getEmailError().setVisible(true);
                ApplicationFacade.showErrorMessage("Errore", "Errore nei campi inseriti", "L'indirizzo email è già stato registrato");
                break;

            case "error:database_error":
            default:
                essentialInfoView.getEmailError().setText("Errore durante la registrazione. Riprova più tardi.");
                essentialInfoView.getEmailError().setVisible(true);
                break;
        }
    }

    public boolean checkFields(EssentialInfoViewAlternative view) {
        boolean result = true;

        // Nascondi tutti gli errori prima di eseguire i controlli
        view.getFirstNameError().setVisible(false);
        view.getLastNameError().setVisible(false);
        view.getEmailError().setVisible(false);
        view.getPhoneNumberError().setVisible(false);

        // Controllo Nome
        String firstName = view.getFirstNameField().getText();
        if (firstName.isBlank()) {
            view.getFirstNameError().setVisible(true);
            result = false;
        }

        // Controllo Cognome
        String lastName = view.getLastNameField().getText();
        if (lastName.isBlank()) {
            view.getLastNameError().setVisible(true);
            result = false;
        }

        // Controllo Email
        String email = view.getEmailField().getText();
        if (email.isBlank() || !Pattern.matches(EMAIL_REGEX, email)) {
            view.getEmailError().setVisible(true);
            result = false;
        }

        // Controllo Numero di Telefono
        String phoneNumber = view.getPhoneNumberField().getText();
        if (phoneNumber.length() != 10 || !phoneNumber.matches("\\d{10}")) {
            view.getPhoneNumberError().setText("Il numero di telefono deve essere di 10 cifre, senza prefisso, italiano.");
            view.getPhoneNumberError().setVisible(true);
            result = false;
        }

        return result;
    }


    private void navigateToNextPage() {
        if (nextPage.equalsIgnoreCase("HomePage")) {
            navigationService.navigateToHomePage(this.navigationService);
        } else if (nextPage.equalsIgnoreCase("ServiceSelection")) {
            navigationService.navigateToServiceSelection(this.navigationService);
        }
    }

    public VBox getRoot() {
        return essentialInfoView.getRoot();
    }
}
