package org.example.application_controller;

import org.example.dao.DaoFactory;
import org.example.dao.GenericDao;
import org.example.entity.Client;
import org.example.exception.UserAlreadyInsertedException;
import org.example.view.EssentialInfoView;

import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Pattern;

public class EssentialInfoController {
    private static final Logger logger = Logger.getLogger(EssentialInfoController.class.getName());
    private final GenericDao<Client> clientDao;
    private static final String EMAIL_REGEX = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$";

    public EssentialInfoController() {
        DaoFactory daoFactory = new DaoFactory();
        this.clientDao = daoFactory.getClientDao();
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

    public Client createClientFromInput(String firstName, String lastName, String email, String phoneNumber) throws UserAlreadyInsertedException {
        Client client = new Client();
        client.setFirstName(firstName);
        client.setLastName(lastName);
        client.setPhoneNumber(phoneNumber);
        client.setEmail(email);

        return client;
    }

    public String registerClient(Client client) {
        try {
            clientDao.create(client);
            logger.info("Cliente registrato con successo: " + client.getEmail());
            return "success";
        } catch (UserAlreadyInsertedException e) {
            logger.warning("Registrazione fallita: " + e.getMessage());
            return "error:client_exists";
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Errore durante la registrazione dell''utente: {0}", e.getMessage());
            return "error:database_error";
        }
    }
}
