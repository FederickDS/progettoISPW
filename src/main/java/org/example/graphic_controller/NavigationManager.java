package org.example.graphic_controller;

import javafx.scene.Scene;
import javafx.stage.Stage;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.logging.Logger;
import org.example.application_controller.BookRoom;
import org.example.entity.StartupSettingsEntity;

import javafx.scene.layout.VBox;

public class NavigationManager implements NavigationService {
    private static NavigationManager instance;
    private Stage stage;
    private final Logger logger = Logger.getLogger(NavigationManager.class.getName());;

    public NavigationManager() {
    }

    public static NavigationManager getInstance() {
        if (instance == null) {
            instance = new NavigationManager();
        }
        return instance;
    }

    @Override
    public void navigateToStartupSettings() {
        StartupSettingsController controller = new StartupSettingsController();
        this.display(controller.getView(), "Impostazioni Iniziali");
    }

    @Override
    public void navigateToHomePage() {
        HomePageController controller = new HomePageController();
        this.display(controller.getView(), "Home Page");
    }

    @Override
    public void navigateToServiceSelection() {
        ServiceSelectionController controller = new ServiceSelectionController();
        this.display(controller.getView(), "Selezione Servizi");
    }

    public void navigateToBookingRoom(BookRoom bookRoom) {
        BookingRoomController controller = new BookingRoomController(bookRoom);
        this.display(controller.getView(), "Prenotazione Camera");
    }

    public void navigateToLogin(String previousPage, String nextPage, String typeOfLogin) {
        LoginController controller = new LoginController(previousPage, nextPage, typeOfLogin);
        this.display(controller.getView(), "Login");
    }

    public void navigateToRegistration(String previousPage, String nextPage, String userType) {
        RegistrationController controller = new RegistrationController(previousPage, nextPage, userType);
        this.display(controller.getView(), "Registrazione");
    }

    public void display(VBox root, String title) {
        try {
            Scene scene = new Scene(root);
            scene.getStylesheets().clear();
            String styleCSS = StartupSettingsEntity.getInstance().getCSSStyle();
            scene.getStylesheets().add(getClass().getResource(styleCSS).toExternalForm());
            stage.setScene(scene);
            stage.setTitle(title);
            stage.show();
        } catch (Exception e) {
            logger.warning("Errore durante il caricamento della vista: " + e.getMessage());
        }
    }

    public String hashWithSHA256(String input) {
        try {
            // Crea un'istanza di MessageDigest per SHA-256
            MessageDigest digest = MessageDigest.getInstance("SHA-256");

            // Calcola l'hash della stringa
            byte[] encodedHash = digest.digest(input.getBytes());

            // Converti i byte dell'hash in una stringa esadecimale
            StringBuilder hexString = new StringBuilder();
            for (byte b : encodedHash) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) {
                    hexString.append('0');
                }
                hexString.append(hex);
            }
            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Errore: Algoritmo SHA-256 non disponibile", e);
        }
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }
}
