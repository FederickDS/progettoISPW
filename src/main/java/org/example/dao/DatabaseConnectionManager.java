package org.example.dao;

import org.example.exception.DatabaseConfigurationException;
import org.example.exception.DatabaseConnectionException;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
import java.util.logging.Logger;

public class DatabaseConnectionManager {
    private static final Logger logger = Logger.getLogger(DatabaseConnectionManager.class.getName());
    private static Connection connection;

    private static String url;
    private static String user;
    private static String password;

    private DatabaseConnectionManager() {
        throw new IllegalStateException("Utility class");
    }

    static {
        // Carica i dati di configurazione
        try (FileInputStream fis = new FileInputStream("config/dbconfig.properties")) {
            Properties properties = new Properties();
            properties.load(fis);

            url = properties.getProperty("db.url");
            user = properties.getProperty("db.user");
            password = properties.getProperty("db.password");
        } catch (IOException e) {
            throw new DatabaseConfigurationException("Impossibile caricare la configurazione del database", e);
        }
    }

    public static Connection getConnection() {
        try {
            if (connection == null || connection.isClosed()) {
                connection = DriverManager.getConnection(url, user, password);
                logger.info("Connessione al database ristabilita.");
            }
        } catch (SQLException e) {
            logger.severe("Errore nella connessione al database: " + e.getMessage());
            throw new DatabaseConnectionException("Impossibile connettersi al database", e);
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
