package org.example.application_controller;

import org.example.bean.LoginBean;
import org.example.dao.GenericDao;
import org.example.dao.DaoFactory;
import org.example.entity.Client;
import org.example.entity.Receptionist;
import org.example.entity.User;
import org.example.exception.WrongLoginCredentialsException;

import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ValidateLogin {
    private final GenericDao<Client> clientDao;
    private final GenericDao<Receptionist> receptionistDao;
    private final Logger logger = Logger.getLogger(ValidateLogin.class.getName());

    public ValidateLogin() {
        DaoFactory daoFactory = new DaoFactory();
        this.clientDao = daoFactory.getClientDao();
        this.receptionistDao = DaoFactory.getReceptionistDao();
    }

    public User validate(LoginBean loginBean) {
        try {
            if (loginBean == null || loginBean.getEmail() == null || loginBean.getEmail().isBlank()) {
                return null;
            }
            String email = loginBean.getEmail();
            String password = loginBean.getPassword();
            String userType = loginBean.getUserType();

            if (userType.equalsIgnoreCase("client")) {
                Client client = clientDao.read(email);
                if (client.getPassword().equals(password)) {
                    return client;
                }
            } else if (userType.equalsIgnoreCase("receptionist")) {
                Receptionist receptionist = receptionistDao.read(email);
                if (receptionist.getPassword().equals(password)) {
                    return receptionist;
                }
            } else if (userType.equalsIgnoreCase("essentialInfo")) {
                return clientDao.read(email);
            }

            throw new WrongLoginCredentialsException("Email o password non corretti");

        } catch (WrongLoginCredentialsException e) {
            throw e;
        } catch (SQLException e) {
            logger.log(Level.WARNING, "Errore durante la validazione dell'utente: ", e);
            return null;
        }
    }
}
