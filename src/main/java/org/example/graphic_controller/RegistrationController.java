package org.example.graphic_controller;

import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.example.entity.User;
import org.example.view.AbstractRegistrationView;
import org.example.view.ClientRegistrationView;
import org.example.view.ReceptionistRegistrationView;
import org.example.entity.Client;
import org.example.entity.Receptionist;
import org.example.application_controller.UserRegistrationController;

import java.time.LocalDate;
import java.util.logging.Logger;

public class RegistrationController {
    private final Logger logger = Logger.getLogger(getClass().getName());
    private AbstractRegistrationView registrationView;
    private final String previousPage;
    private final String nextPage;

    public RegistrationController(String previousPage, String nextPage, String userType) {
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
        String firstName = registrationView.getFirstNameField().getText();
        String lastName = registrationView.getLastNameField().getText();
        String email = registrationView.getEmailField().getText();
        String password = registrationView.getPasswordField().getText();
        //controlli ai campi
        if (firstName.isBlank() || lastName.isBlank() || email.isBlank() || password.isBlank()) {
            logger.warning("Uno o più campi obbligatori sono mancanti.");
            // Mostra un messaggio di errore nella view (nella classe `AbstractRegistrationView`).
            return;
        }
        //conversione sha password
        password = NavigationManager.getInstance().hashWithSHA256(password);

        User newUser = createUserFromInput(firstName,lastName,email,password);

        // Salva l'utente nel sistema
        if(newUser!=null){
            UserRegistrationController appController = new UserRegistrationController();
            String result = appController.registerUser(newUser);

            switch (result) {
                case "success" -> navigateToNextPage();
                case "error:phone_exists" -> registrationView.showPhoneErrorMessage("Il numero di telefono è già registrato.");
                case "error:database_error" -> registrationView.showPhoneErrorMessage("Errore durante la registrazione. Riprova più tardi.");
                default -> registrationView.showPhoneErrorMessage("Errore sconosciuto.");
            }
        }
    }

    private User createUserFromInput(String firstName, String lastName, String email, String password) {
        if (registrationView instanceof ClientRegistrationView clientView) {
            Client newClient = new Client();
            newClient.setFirstName(firstName);
            newClient.setLastName(lastName);
            newClient.setEmail(email);
            newClient.setPassword(password);

            // Conversione della data di nascita
            String birthDateText = clientView.getBirthDateField().getText();
            try {
                LocalDate birthDate = LocalDate.parse(birthDateText); // Analizza la stringa nel formato "yyyy-MM-dd"
                newClient.setBirthDate(birthDate);
            } catch (Exception e) {
                logger.warning("Formato data di nascita non valido: " + birthDateText);
                return null; // Fallisce la registrazione in caso di formato errato
            }

            newClient.setTaxCode(clientView.getTaxCodeField().getText());
            newClient.setPhoneNumber(clientView.getPhoneNumberField().getText());
            return newClient;
        } else if (registrationView instanceof ReceptionistRegistrationView receptionistView) {
            Receptionist newReceptionist = new Receptionist();
            newReceptionist.setFirstName(firstName);
            newReceptionist.setLastName(lastName);
            newReceptionist.setEmail(email);
            newReceptionist.setPassword(password);
            return newReceptionist;
        }
        return null;
    }

    private void navigateToNextPage() {
        switch (nextPage) {
            case "HomePage" -> NavigationManager.getInstance().navigateToHomePage();
            case "ServiceSelection" -> NavigationManager.getInstance().navigateToServiceSelection();
            default -> logger.warning("Pagina successiva non definita");
        }
    }

    private void navigateBack() {
        if (previousPage == null || previousPage.isBlank()) {
            logger.warning("Pagina precedente non definita. Operazione annullata.");
            return;
        }

        switch (previousPage) {
            case "StartupSettings" -> NavigationManager.getInstance().navigateToStartupSettings();
            case "HomePage" -> NavigationManager.getInstance().navigateToHomePage();
            case "ServiceSelection" -> NavigationManager.getInstance().navigateToServiceSelection();
            default -> logger.warning("Pagina precedente sconosciuta");
        }
    }

    private void goToLogin() {
        NavigationManager.getInstance().navigateToLogin(previousPage,nextPage,registrationView.getType());
    }

    public VBox getView(){
        return this.registrationView.getRoot();
    }
}
