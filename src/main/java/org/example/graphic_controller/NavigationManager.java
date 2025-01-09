package org.example.graphic_controller;

import javafx.scene.Scene;
import javafx.stage.Stage;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.logging.Logger;
import org.example.application_controller.BookRoom;
import org.example.entity.StartupSettingsEntity;

import javafx.scene.layout.VBox;


public class NavigationManager implements NavigationService{
    private final Stage stage;
    private final Logger logger;

    public NavigationManager(Stage stage) {
        this.stage = stage;
        this.logger = Logger.getLogger(NavigationManager.class.getName());
    }

    @Override
    public void navigateToStartupSettings(){
        new StartupSettingsController(this.stage, this).loadStartupSettings();
    }

    @Override
    public void navigateToHomePage() {
        new HomePageController(this.stage, this).loadHomePage();
    }

    @Override
    public void navigateToServiceSelection() {
        new ServiceSelectionController(this.stage, this).loadServiceSelection();
    }

    public void navigateToBookingRoom(BookRoom bookRoom) {
        new BookingRoomController(this.stage, this, bookRoom).loadBookingRoom();
    }

    public void navigateToLogin(String previousPage, String nextPage, String type){
        new LoginController(this.stage,this,previousPage,nextPage).loadLoginView(type);
    }

    public void navigateToRegistration(String previousPage, String nextPage, String userType){
        new RegistrationController(this.stage,this,previousPage,nextPage).loadRegistrationView(userType);
    }


    public void display(Stage stage, VBox root, String title) {
        try {
            Scene scene = new Scene(root);
            //modifica colori
            scene.getStylesheets().clear();
            String styleCSS = StartupSettingsEntity.getInstance().getCSSStyle();
            scene.getStylesheets().add(getClass().getResource(styleCSS).toExternalForm());
            stage.setScene(scene);
            stage.setTitle(title);
            stage.show();
        }catch (Exception e){
            this.logger.warning("Date non valide. Selezionare entrambe le date.");
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


    protected Stage getStage() {
        return this.stage;
    }
}
