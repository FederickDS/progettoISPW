package org.example.graphic_controller;

import javafx.scene.Scene;
import javafx.stage.Stage;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.logging.Logger;
import org.example.application_controller.BookRoom;
import org.example.entity.StartupSettingsEntity;

import javafx.scene.layout.VBox;
import org.example.exception.HashingException;

public class NavigationManager implements NavigationService {
    private Stage stage;
    private final Logger logger = Logger.getLogger(NavigationManager.class.getName());

    public NavigationManager(Stage stage) {
        this.stage = stage;
    }

    @Override
    public void navigateToStartupSettings(NavigationService navigationService) {
        StartupSettingsController controller = new StartupSettingsController(navigationService);
        this.display(controller.getRoot(), "Impostazioni Iniziali");
    }

    @Override
    public void navigateToHomePage(NavigationService navigationService) {
        HomePageController controller = new HomePageController(navigationService);
        this.display(controller.getRoot(), "Home Page");
    }

    @Override
    public void navigateToServiceSelection(NavigationService navigationService) {
        ServiceSelectionController controller = new ServiceSelectionController(navigationService);
        this.display(controller.getRoot(), "Selezione Servizi");
    }

    public void navigateToRoomBookingOptions(NavigationService navigationService, String previousPage, String nextPage) {
        RoomBookingOptionsController controller = new RoomBookingOptionsController(navigationService, previousPage, nextPage);
        this.display(controller.getRoot(), "Opzioni Prenotazione Stanza");
    }

    public void navigateToBookingRoom(NavigationService navigationService, BookRoom bookRoom) {
        BookingRoomController controller = new BookingRoomController(navigationService, bookRoom);
        this.display(controller.getRoot(), "Prenotazione Camera");
    }

    public void navigateToLogin(NavigationService navigationService, String previousPage, String nextPage, String typeOfLogin) {
        LoginController controller = new LoginController(navigationService, previousPage, nextPage, typeOfLogin);
        this.display(controller.getRoot(), "Login");
    }

    public void navigateToRegistration(NavigationService navigationService, String previousPage, String nextPage, String userType) {
        RegistrationController controller = new RegistrationController(navigationService, previousPage, nextPage, userType);
        this.display(controller.getRoot(), "Registrazione");
    }

    public void navigateToEssentialInfo(NavigationService navigationService, String previousPage, String nextPage){
        EssentialInfoGraphicController controller = new EssentialInfoGraphicController(navigationService, previousPage, nextPage);
        this.display(controller.getRoot(), "Inserisci Informazioni Essenziali");
    }

    public void navigateToNotImplemented(NavigationService navigationService, String previousPage){
        NotImplementedController controller = new NotImplementedController(navigationService, previousPage);
        this.display(controller.getRoot(), "Non implementato (come impostare titolo)");
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

    public String hashWithSHA256(String input){
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
            throw new HashingException("Errore: Algoritmo SHA-256 non disponibile", e);
        }
    }

    public void navigateBack(String previousPage, NavigationService navigationService) {
        if (previousPage == null || previousPage.isBlank()) {
            logger.warning("Pagina precedente non definita. Operazione annullata.");
            return;
        }

        switch (previousPage) {
            case "StartupSettings" -> navigationService.navigateToStartupSettings(navigationService);
            case "HomePage" -> navigationService.navigateToHomePage(navigationService);
            case "NotImplemented" -> navigationService.navigateToNotImplemented(navigationService,previousPage);
            default -> logger.warning("Pagina precedente sconosciuta");
        }
    }


    public void setStage(Stage stage) {
        this.stage = stage;
    }
}
