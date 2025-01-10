package org.example.application_controller;

import org.example.dao.ClientDaoDB;
import org.example.dao.ReceptionistDaoDB;
import org.example.dao.DatabaseConnectionManager;
import org.example.entity.Client;
import org.example.entity.Receptionist;
import org.example.entity.User;

import java.sql.Connection;
import java.sql.SQLException;

public class ValidateLogin {
    public User validate(String email, String password, String userType) {
        try (Connection connection = DatabaseConnectionManager.getConnection()) {
            if (userType.equalsIgnoreCase("client")) {
                ClientDaoDB clientDao = new ClientDaoDB(connection);
                Client client = clientDao.read(email);
                if (client != null && client.getPassword().equals(password)) {
                    return client;
                }
            } else if (userType.equalsIgnoreCase("receptionist")) {
                ReceptionistDaoDB receptionistDao = new ReceptionistDaoDB(connection);
                Receptionist receptionist = receptionistDao.read(email);
                if (receptionist != null && receptionist.getPassword().equals(password)) {
                    return receptionist;
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error validating user ", e);
        }
        return null; // Login fallito
    }
}
