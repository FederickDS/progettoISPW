package org.example.application_controller;

import org.example.dao.DaoFactory;
import org.example.dao.GenericDao;
import org.example.entity.Client;
import org.example.entity.Receptionist;
import org.example.entity.User;
import org.example.graphic_controller.NavigationService;
import org.example.graphic_controller.SessionManager;
import org.example.view.ClientRegistrationView;
import org.example.view.ReceptionistRegistrationView;
import org.example.view.AbstractRegistrationView;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Pattern;


public class UserRegistrationController {
    private static final String EMAIL_REGEX = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";
    private final Logger logger = Logger.getLogger(getClass().getName());
    private final GenericDao<Client> clientDao;
    private final GenericDao<Receptionist> receptionistDao;

    public UserRegistrationController() {
        // Ottenere i DAO utilizzando DaoFactory
        this.clientDao = DaoFactory.getClientDao();
        this.receptionistDao = DaoFactory.getReceptionistDao();
    }

    public String registerUser(User user) {
        try {
            if (user instanceof Client client) {
                clientDao.create(client);
                SessionManager.getInstance().setCredentials(client.getEmail(),client.getPassword(),client.getUserType());
                logger.info("Client registrato con successo: " + client.getEmail());
                return "success";
            } else if (user instanceof Receptionist receptionist) {
                receptionistDao.create(receptionist);
                SessionManager.getInstance().setCredentials(receptionist.getEmail(),receptionist.getPassword(),receptionist.getUserType());
                logger.info("Receptionist registrato con successo: " + receptionist.getEmail());
                return "success";
            } else {
                logger.warning("Tipo di utente non riconosciuto: " + user.getClass().getName());
                return "error:unknown_user_type";
            }
        } catch (SQLException e) {
            if (e.getMessage().contains("Il numero di telefono è già registrato")) {
                logger.log(Level.WARNING, "Registrazione fallita: ", e);
                return "error:phone_exists";
            } else {
                logger.log(Level.SEVERE, "Errore durante la registrazione dell'utente: ", e);
                return "error:database_error";
            }
        }
    }

    public User createUserFromInput(AbstractRegistrationView registrationView, NavigationService navigationService){
        if(registrationView instanceof ClientRegistrationView clientView) {
            Client newClient = new Client();
            newClient.setFirstName(clientView.getFirstNameField().getText());
            newClient.setLastName(clientView.getLastNameField().getText());
            newClient.setEmail(clientView.getEmailField().getText());
            newClient.setPhoneNumber(clientView.getPhoneNumberField().getText());
            newClient.setPassword(navigationService.hashWithSHA256(registrationView.getPasswordField().getText()));
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
            return newClient;
        } else if (registrationView instanceof ReceptionistRegistrationView receptionistView){
            Receptionist newReceptionist = new Receptionist();
            newReceptionist.setFirstName(receptionistView.getFirstNameField().getText());
            newReceptionist.setLastName(receptionistView.getLastNameField().getText());
            newReceptionist.setEmail(receptionistView.getEmailField().getText());
            newReceptionist.setPhoneNumber(receptionistView.getPhoneNumberField().getText());
            newReceptionist.setPassword(navigationService.hashWithSHA256(registrationView.getPasswordField().getText()));
            return newReceptionist;
        }else{
            return null;
        }
    }

    private boolean checkBasicCredentials(AbstractRegistrationView registrationView) {
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

    public boolean checkSpecificCredentials(AbstractRegistrationView registrationView){
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

    public boolean checkAllCredentials(AbstractRegistrationView registrationView) {
        boolean result = checkBasicCredentials(registrationView);
        if(!checkSpecificCredentials(registrationView)){
            result = false;
        }
        return result;
    }

}
