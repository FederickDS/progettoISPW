package org.example.application_controller;

import org.example.bean.UserRegistrationBean;
import org.example.dao.DaoFactory;
import org.example.dao.GenericDao;
import org.example.entity.Client;
import org.example.entity.Receptionist;
import org.example.entity.User;
import org.example.graphic_controller.SessionManager;

import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;


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

    public String registerUser(UserRegistrationBean userRegistrationBean) {
        User user = createUserFromBean(userRegistrationBean);
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

    public User createUserFromBean(UserRegistrationBean userRegistrationBean) {
        if ("client".equalsIgnoreCase(userRegistrationBean.getUserType())) {
            Client newClient = new Client();
            newClient.setFirstName(userRegistrationBean.getFirstName());
            newClient.setLastName(userRegistrationBean.getLastName());
            newClient.setEmail(userRegistrationBean.getEmail());
            newClient.setPhoneNumber(userRegistrationBean.getPhoneNumber());
            newClient.setPassword(User.hashWithSHA256(userRegistrationBean.getPassword()));
            newClient.setBirthDate(userRegistrationBean.getBirthDate());
            newClient.setTaxCode(userRegistrationBean.getTaxCode());
            return newClient;
        } else if ("receptionist".equalsIgnoreCase(userRegistrationBean.getUserType())) {
            Receptionist newReceptionist = new Receptionist();
            newReceptionist.setFirstName(userRegistrationBean.getFirstName());
            newReceptionist.setLastName(userRegistrationBean.getLastName());
            newReceptionist.setEmail(userRegistrationBean.getEmail());
            newReceptionist.setPhoneNumber(userRegistrationBean.getPhoneNumber());
            newReceptionist.setPassword(User.hashWithSHA256(userRegistrationBean.getPassword()));
            return newReceptionist;
        }
        return null;
    }

}
