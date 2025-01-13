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
        //controllo campi corretti
        if((!checkAllCredentials())){
            return;
        }
        String firstName = registrationView.getFirstNameField().getText();
        String lastName = registrationView.getLastNameField().getText();
        String email = registrationView.getEmailField().getText();
        String password = registrationView.getPasswordField().getText();
        String phoneNumber = registrationView.getPhoneNumberField().getText();
        password = navigationService.hashWithSHA256(password);

        User newUser = createUserFromInput(firstName,lastName,email,password,phoneNumber);

        // Salva l'utente nel sistema (controller applicativo)
        if(newUser!=null){
            UserRegistrationController appController = new UserRegistrationController();
            String result = appController.registerUser(newUser);

            switch (result) {
                case "success" -> navigateToNextPage(newUser);
                case "error:phone_exists" -> registrationView.showPhoneNumberError("Il numero di telefono è già registrato.");
                case "error:database_error" -> registrationView.showDatabaseError("Errore durante la registrazione. Riprova più tardi.");
                default -> registrationView.showDatabaseError("Errore sconosciuto.");
            }
        }
    }

    private User createUserFromInput(String firstName, String lastName, String email, String password, String phoneNumber) {
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
            newClient.setPhoneNumber(phoneNumber);
            return newClient;
        } else if (registrationView instanceof ReceptionistRegistrationView) {
            Receptionist newReceptionist = new Receptionist();
            newReceptionist.setFirstName(firstName);
            newReceptionist.setLastName(lastName);
            newReceptionist.setEmail(email);
            newReceptionist.setPhoneNumber(phoneNumber);
            newReceptionist.setPassword(password);
            return newReceptionist;
        }
        return null;
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

    private boolean checkBasicCredentials() {
        boolean result = true;
        registrationView.hideAllErrors();
        String firstName = registrationView.getFirstNameField().getText();
        if(firstName.isBlank()){
            registrationView.showFirstNameError();
            result = false;
        }
        String lastName = registrationView.getLastNameField().getText();
        if(lastName.isBlank()){
            registrationView.showLastNameError();
            result = false;
        }
        String email = registrationView.getEmailField().getText();
        if((email.isBlank())||(!Pattern.matches(EMAIL_REGEX, email))){
            registrationView.showEmailError();
            result = false;
        }
        String password = registrationView.getPasswordField().getText();
        if(password.isBlank()||(password.length()<8)||(password.length()>16)){
            registrationView.showPasswordError("Inserire una password tra gli 8 e i 16 caratteri");
            result = false;
        }
        String repeatPassword = registrationView.getRepeatPasswordField().getText();
        if(!(password.equals(repeatPassword))){
            registrationView.showRepeatPasswordError("Non hai ripetuto la stessa password");
            result = false;
        }
        String phoneNumber = registrationView.getPhoneNumberField().getText();
        if(phoneNumber.length()!=10){
            registrationView.showPhoneNumberError("Il numero di telefono inserito deve essere di 10 caratteri, senza prefisso, italiano.");
            result = false;
        }
        return result;
    }

    public boolean checkSpecificCredentials(){
        boolean result = true;
        registrationView.hideSpecificErrors();
        if(registrationView instanceof ClientRegistrationView clientView){
            String taxCode = clientView.getTaxCodeField().getText();
            if(taxCode.length()!=16){
                clientView.showTaxCodeError("Inserire il codice fiscale");
                result = false;
            }
            // Conversione della data di nascita
            String birthDateText = clientView.getBirthDateField().getText();
            try {
                LocalDate.parse(birthDateText); // Analizza la stringa nel formato "yyyy-MM-dd"
            } catch (Exception e) {
                clientView.showBirthDateError("La stringa di data non è nel formato corretto");
                result = false;
            }
        }
        return result;
    }

    public boolean checkAllCredentials() {
        boolean result = true;
        if(!checkBasicCredentials()){
            result = false;
        }
        if(!checkSpecificCredentials()){
            result = false;
        }
        return result;
    }

    private void goToLogin() {
        navigationService.navigateToLogin(this.navigationService, previousPage,nextPage,registrationView.getType());
    }

    public VBox getView(){
        return this.registrationView.getRoot();
    }
}
