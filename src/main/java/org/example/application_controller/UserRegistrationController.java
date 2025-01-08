package org.example.application_controller;

import org.example.dao.ClientDaoDB;
import org.example.dao.ReceptionistDaoDB;
import org.example.dao.GenericDao;
import org.example.entity.Client;
import org.example.entity.Receptionist;
import org.example.entity.User;
import org.example.dao.DatabaseConnectionManager;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class UserRegistrationController {
    private final Logger logger = Logger.getLogger(getClass().getName());
    private final GenericDao<Client> clientDao;
    private final GenericDao<Receptionist> receptionistDao;

    public UserRegistrationController() {
        Connection connection = DatabaseConnectionManager.getConnection();
        this.clientDao = new ClientDaoDB(connection);
        this.receptionistDao = new ReceptionistDaoDB(connection);
    }

    public String registerUser(User user) {
        try {
            if (user instanceof Client client) {
                clientDao.create(client);
                logger.info("Client registrato con successo: " + client.getEmail());
                return "success";
            } else if (user instanceof Receptionist receptionist) {
                receptionistDao.create(receptionist);
                logger.info("Receptionist registrato con successo: " + receptionist.getEmail());
                return "success";
            } else {
                logger.warning("Tipo di utente non riconosciuto: " + user.getClass().getName());
                return "error:unknown_user_type";
            }
        } catch (SQLException e) {
            if (e.getMessage().contains("Il numero di telefono è già registrato")) {
                logger.warning("Registrazione fallita: " + e.getMessage());
                return "error:phone_exists";
            } else {
                logger.log(Level.SEVERE, "Errore durante la registrazione dell'utente: " + user.getEmail(), e);
                return "error:database_error";
            }
        }
    }

}
