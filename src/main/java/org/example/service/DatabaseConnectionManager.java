package org.example.service;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Logger;

public class DatabaseConnectionManager {
    private static final Logger logger = Logger.getLogger(DatabaseConnectionManager.class.getName());
    private static Connection connection;

    private static final String URL = "jdbc:mysql://localhost:3306/Hotel";
    private static final String USER = "root";
    private static final String PASSWORD = "federico123";

    public static Connection getConnection() {
        if (connection == null) {
            try {
                connection = DriverManager.getConnection(URL, USER, PASSWORD);
                logger.info("Connessione al database stabilita.");
            } catch (SQLException e) {
                logger.severe("Errore nella connessione al database: " + e.getMessage());
                throw new RuntimeException("Impossibile connettersi al database", e);
            }
        }
        return connection;
    }

    public static void closeConnection() {
        if (connection != null) {
            try {
                connection.close();
                connection = null;
                logger.info("Connessione al database chiusa.");
            } catch (SQLException e) {
                logger.warning("Errore durante la chiusura della connessione: " + e.getMessage());
            }
        }
    }
}
