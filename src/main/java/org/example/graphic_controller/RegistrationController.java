package org.example.graphic_controller;

import javafx.scene.layout.VBox;
import org.example.entity.User;
import org.example.view.AbstractRegistrationView;
import org.example.view.ClientRegistrationView;
import org.example.view.ReceptionistRegistrationView;
import org.example.entity.Client;
import org.example.entity.Receptionist;
import org.example.application_controller.UserRegistrationController;

import java.time.LocalDate;
import java.util.logging.Logger;
import java.util.regex.Pattern;

public class RegistrationController {
    private static final String EMAIL_REGEX = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";
    private final Logger logger = Logger.getLogger(getClass().getName());
    private AbstractRegistrationView registrationView;
    private final String previousPage;
    private final String nextPage;
    private final NavigationService navigationService;

    public RegistrationController(NavigationService navigationService, String previousPage, String nextPage, String userType) {
        this.navigationService = navigationService;
        this.previousPage = previousPage;
        this.nextPage = nextPage;
        //meccanismo di scelta
        if (userType.equalsIgnoreCase("client")) {
            this.registrationView = new ClientRegistrationView();
        } else {
            this.registrationView = new ReceptionistRegistrationView();
        }
        // Aggiungi gestione eventi
        addEventHandlers();
    }

    private void addEventHandlers() {
        // Bottone di Registrazione
        registrationView.getRegisterButton().setOnAction(e -> handleRegistration());

        // Gestione navigazione indietro
        registrationView.getCancelButton().setOnAction(e -> navigateBack());

        registrationView.getLoginButton().setOnAction(e -> goToLogin());
    }

    private void handleRegistration() {
        UserRegistrationController appController = new UserRegistrationController();
        if(!appController.checkAllCredentials(registrationView)){
            return;
        }
        User user = appController.createUserFromInput(registrationView, navigationService);
        String result = appController.registerUser(user);
            switch (result) {
                case "success" -> navigateToNextPage(user);
                case "error:phone_exists" -> registrationView.showPhoneNumberError("Il numero di telefono è già registrato.");
                case "error:database_error" -> registrationView.showDatabaseError("Errore durante la registrazione. Riprova più tardi.");
                default -> registrationView.showDatabaseError("Errore sconosciuto.");
        }
    }

    private void navigateToNextPage(User newUser) {
        switch (nextPage) {
            case "HomePage" -> navigationService.navigateToHomePage(this.navigationService);
            case "ServiceSelection" -> navigationService.navigateToServiceSelection(this.navigationService, newUser);
            default -> logger.warning("Pagina successiva non definita");
        }
    }

    private void navigateBack() {
        if (previousPage == null || previousPage.isBlank()) {
            logger.warning("Pagina precedente non definita. Operazione annullata.");
            return;
        }

        switch (previousPage) {
            case "StartupSettings" -> navigationService.navigateToStartupSettings(this.navigationService);
            case "HomePage" -> navigationService.navigateToHomePage(this.navigationService);
            default -> logger.warning("Pagina precedente sconosciuta");
        }
    }

    private void goToLogin() {
        navigationService.navigateToLogin(this.navigationService, previousPage,nextPage,registrationView.getType());
    }

    public VBox getView(){
        return this.registrationView.getRoot();
    }
}
