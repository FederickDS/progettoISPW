package org.example.view.control;

import javafx.stage.Stage;
import org.example.entity.User;
import org.example.view.AbstractRegistrationView;
import org.example.view.ClientRegistrationView;
import org.example.view.ReceptionistRegistrationView;
import org.example.entity.Client;
import org.example.entity.Receptionist;

import java.time.LocalDateTime;
import java.util.logging.Logger;

public class RegistrationController {
    private final Logger logger = Logger.getLogger(getClass().getName());
    private final Stage stage;
    private final NavigationService navigationService;
    private AbstractRegistrationView registrationView;
    private final String previousPage;
    private final String nextPage;

    public RegistrationController(Stage stage, NavigationService navigationService, String previousPage, String nextPage) {
        this.stage = stage;
        this.navigationService = navigationService;
        this.previousPage = previousPage;
        this.nextPage = nextPage;
    }

    public void loadRegistrationView(String userType) {
        if (userType.equalsIgnoreCase("client")) {
            this.registrationView = new ClientRegistrationView();
        } else {
            this.registrationView = new ReceptionistRegistrationView();
        }

        // Mostra la view
        navigationService.display(stage, registrationView.getVBox(), "Registrazione");

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

        if (firstName.isBlank() || lastName.isBlank() || email.isBlank() || password.isBlank()) {
            logger.warning("Uno o più campi obbligatori sono mancanti.");
            // Mostra un messaggio di errore nella view (aggiungibile nella tua classe `AbstractRegistrationView`).
            return;
        }

        User newUser = createUserFromInput(firstName,lastName,email,password);

        // Salva l'utente nel sistema (puoi aggiungere la logica di salvataggio qui)
        if(newUser!=null){
            logger.info("Registrazione riuscita per: " + newUser.getEmail());
            navigateToNextPage();
        }
    }

    private User createUserFromInput(String firstName, String lastName, String email, String password) {
        if (registrationView instanceof ClientRegistrationView clientView) {
            Client newClient = new Client();
            newClient.setFirstName(firstName);
            newClient.setLastName(lastName);
            newClient.setEmail(email);
            newClient.setPassword(password);
            newClient.setBirthDate(LocalDateTime.parse(clientView.getBirthDateField().getText()));
            newClient.setTaxCode(clientView.getTaxCodeField().getText());
            newClient.setPhoneNumber(clientView.getPhoneNumberField().getText());
            return newClient;
        } else if (registrationView instanceof ReceptionistRegistrationView receptionistView) {
            Receptionist newReceptionist = new Receptionist();
            newReceptionist.setFirstName(firstName);
            newReceptionist.setLastName(lastName);
            newReceptionist.setEmail(email);
            newReceptionist.setPassword(password);
            newReceptionist.setEmployeeId(receptionistView.getEmployeeIdField().getText());
            return newReceptionist;
        }
        return null;
    }

    private void navigateToNextPage() {
        switch (nextPage) {
            case "HomePage" -> navigationService.navigateToHomePage();
            case "ServiceSelection" -> navigationService.navigateToServiceSelection();
            default -> logger.warning("Pagina successiva non definita");
        }
    }

    private void navigateBack() {
        if (previousPage == null || previousPage.isBlank()) {
            logger.warning("Pagina precedente non definita. Operazione annullata.");
            return;
        }

        switch (previousPage) {
            case "StartupSettings" -> navigationService.navigateToStartupSettings();
            case "HomePage" -> navigationService.navigateToHomePage();
            case "ServiceSelection" -> navigationService.navigateToServiceSelection();
            default -> logger.warning("Pagina precedente sconosciuta");
        }
    }

    private void goToLogin() {
        navigationService.navigateToLogin(previousPage,nextPage,registrationView.getType());
    }
}
