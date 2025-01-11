package org.example.application_controller;

import org.example.dao.GenericDao;
import org.example.dao.DaoFactory;
import org.example.entity.Client;
import org.example.entity.Receptionist;
import org.example.entity.User;

import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ValidateLogin {
    private final GenericDao<Client> clientDao;
    private final GenericDao<Receptionist> receptionistDao;
    private final Logger logger = Logger.getLogger(ValidateLogin.class.getName());

    public ValidateLogin() {
        this.clientDao = DaoFactory.getClientDao();
        this.receptionistDao = DaoFactory.getReceptionistDao();
    }

    public User validate(String email, String password, String userType) {
        try {
            if (userType.equalsIgnoreCase("client")) {
                Client client = clientDao.read(email);
                if (client != null && client.getPassword().equals(password)) {
                    return client;
                }
            } else if (userType.equalsIgnoreCase("receptionist")) {
                Receptionist receptionist = receptionistDao.read(email);
                if (receptionist != null && receptionist.getPassword().equals(password)) {
                    return receptionist;
                }
            }
        } catch (SQLException e) {
            logger.log(Level.WARNING,"Errore durante la validazione dell'utente: ", e);
        }
        return null; // Login fallito
    }
}
