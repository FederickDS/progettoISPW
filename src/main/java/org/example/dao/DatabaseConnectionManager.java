package org.example.dao;

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

    static {
        // Carica i dati di configurazione
        try (FileInputStream fis = new FileInputStream("config/dbconfig.properties")) {
            Properties properties = new Properties();
            properties.load(fis);

            url = properties.getProperty("db.url");
            user = properties.getProperty("db.user");
            password = properties.getProperty("db.password");
        } catch (IOException e) {
            logger.severe("Errore durante il caricamento della configurazione del database: " + e.getMessage());
            throw new RuntimeException("Impossibile caricare la configurazione del database", e);
        }
    }

    public static Connection getConnection() {
        if (connection == null) {
            try {
                connection = DriverManager.getConnection(url, user, password);
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