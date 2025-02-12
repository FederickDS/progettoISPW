package org.example.graphic_controller;

import javafx.scene.layout.VBox;
import org.example.application_controller.EssentialInfoController;
import org.example.entity.Client;
import org.example.facade.ApplicationFacade;
import org.example.view.EssentialInfoView;

import java.util.regex.Pattern;

public class EssentialInfoGraphicController {
    private final EssentialInfoView essentialInfoView;
    private final String previousPage;
    private final String nextPage;
    private final NavigationService navigationService;
    private static final String EMAIL_REGEX = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$";

    public EssentialInfoGraphicController(NavigationService navigationService, String previousPage, String nextPage) {
        this.navigationService = navigationService;
        this.previousPage = previousPage;
        this.nextPage = nextPage;
        this.essentialInfoView = new EssentialInfoView();
        essentialInfoView.hideBasicFields();
        addEventHandlers();
    }

    private void addEventHandlers() {
        essentialInfoView.getRegisterButton().setOnAction(e -> handleRegistration());
        essentialInfoView.getCancelButton().setOnAction(e -> this.navigationService.navigateBack(previousPage,this.navigationService));
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
                // Salvo le credenziali nella sessione
                SessionManager.getInstance().setCredentials(
                        essentialInfoView.getEmailField().getText(),
                        essentialInfoView.getPasswordField().getText(),
                        essentialInfoView.getType()
                );
                navigateToNextPage();
                break;

            case "error:client_exists":
                essentialInfoView.showDatabaseError("Questo indirizzo email è già registrato.");
                ApplicationFacade.showErrorMessage("Errore","Errore nei campi inseriti","L'indirizzo email è già stato registrato");
                break;

            case "error:database_error":
            default:
                essentialInfoView.showDatabaseError("Errore durante la registrazione. Riprova più tardi.");
                break;
        }
    }

    public boolean checkFields(EssentialInfoView view) {
        boolean result = true;
        view.hideAllErrors();

        String firstName = view.getFirstNameField().getText();
        if (firstName.isBlank()) {
            view.showFirstNameError();
            result = false;
        }

        String lastName = view.getLastNameField().getText();
        if (lastName.isBlank()) {
            view.showLastNameError();
            result = false;
        }

        String email = view.getEmailField().getText();
        if (email.isBlank() || !Pattern.matches(EMAIL_REGEX, email)) {
            view.showEmailError();
            result = false;
        }

        String phoneNumber = view.getPhoneNumberField().getText();
        if (phoneNumber.length() != 10) {
            view.showPhoneNumberError("Il numero di telefono deve essere di 10 cifre, senza prefisso, italiano.");
            result = false;
        }

        return result;
    }


    private void navigateToNextPage() {
        // Passa l'utente autenticato alla pagina successiva o memorizzalo in una sessione
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
