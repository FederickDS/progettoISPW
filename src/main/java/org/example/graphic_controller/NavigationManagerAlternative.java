package org.example.graphic_controller;

import javafx.scene.Scene;
import javafx.stage.Stage;
import java.util.logging.Logger;
import javafx.scene.layout.VBox;
import org.example.application_controller.BookRoom;

public class NavigationManagerAlternative implements NavigationService {
    private String interfaceOption = "color";
    private Stage stage;
    private final Logger logger = Logger.getLogger(NavigationManagerAlternative.class.getName());

    public NavigationManagerAlternative(Stage stage) {
        this.stage = stage;
    }

    public Stage getStage(){
        return stage;
    }

    public void setInterfaceOption(String interfaceOption) {
        this.interfaceOption = interfaceOption;
    }

    public String getCSSStyle() {
        String cssFile;
        if (this.interfaceOption.equals("color")) {
            cssFile = "/style/color-mode.css";
        } else {
            cssFile = "/style/bw-mode.css";
        }
        return cssFile;
    }

    public void navigateBack(String previousPage, NavigationService navigationService) {
        if (previousPage == null || previousPage.isBlank()) {
            logger.warning("Pagina precedente non definita. Operazione annullata.");
            return;
        }

        switch (previousPage) {
            case "StartupSettings" -> navigationService.navigateToStartupSettings(navigationService);
            case "HomePage" -> navigationService.navigateToHomePage(navigationService);
            case "NotImplemented" -> navigationService.navigateToNotImplemented(navigationService, previousPage);
            case "BookingRoomController" -> navigationService.navigateToBookingRoom(navigationService, null);
            default -> logger.warning("Pagina precedente sconosciuta");
        }
    }

    public void display(VBox root, String title) {
        try {
            Scene scene = new Scene(root);
            scene.getStylesheets().clear();
            String styleCSS = getCSSStyle();
            scene.getStylesheets().add(getClass().getResource(styleCSS).toExternalForm());
            stage.setScene(scene);
            stage.setTitle(title);
            stage.show();
        } catch (Exception e) {
            logger.warning("Errore durante il caricamento della vista: " + e.getMessage());
        }
    }

    @Override
    public void navigateToStartupSettings(NavigationService navigationService) {
        StartupSettingsController controller = new StartupSettingsController(navigationService);
        this.display(controller.getRoot(), "Impostazioni Iniziali");
    }

    public void navigateToNotImplemented(NavigationService navigationService, String previousPage) {
        NotImplementedController controller = new NotImplementedController(navigationService, previousPage);
        this.display(controller.getRoot(), "Non implementato (come impostare titolo)");
    }

    @Override
    public void navigateToHomePage(NavigationService navigationService) {
        HomePageAlternativeController controller = new HomePageAlternativeController(navigationService);
        this.display(controller.getRoot(), "Home Page (Alternativa)");
    }

    @Override
    public void navigateToServiceSelection(NavigationService navigationService) {
        ServiceSelectionControllerAlternative controller = new ServiceSelectionControllerAlternative(navigationService);
        this.display(controller.getRoot(), "Selezione Servizi");
    }

    public void navigateToRoomBookingOptions(NavigationService navigationService, String previousPage, String nextPage) {
        RoomBookingOptionsControllerAlternative controller = new RoomBookingOptionsControllerAlternative(navigationService, previousPage, nextPage);
        this.display(controller.getRoot(), "Opzioni Prenotazione Stanza");
    }

    public void navigateToBookingRoom(NavigationService navigationService, BookRoom bookRoom) {
        BookingRoomControllerAlternative controller = new BookingRoomControllerAlternative(navigationService, bookRoom);
        this.display(controller.getRoot(), "Prenotazione Camera (Alternativa)");
    }

    public void navigateToReservationPayment(NavigationService navigationService, BookRoom bookRoom) {
        ReservationPaymentControllerAlternative controller = new ReservationPaymentControllerAlternative(navigationService, bookRoom);
        this.display(controller.getRoot(), "Pagamento prenotazione");
    }

    public void navigateToLogin(NavigationService navigationService, String previousPage, String nextPage, String userType){
        LoginAlternativeController controller = new LoginAlternativeController(navigationService, previousPage, nextPage, userType);
        this.display(controller.getRoot(), "Login Alternativo");
    }

    public void navigateToRegistration(NavigationService navigationService, String previousPage, String nextPage, String userType) {
        RegistrationAlternativeController controller = new RegistrationAlternativeController(navigationService, previousPage, nextPage, userType);
        this.display(controller.getRoot(), "Registrazione (secondaria)");
    }

    public void navigateToEssentialInfo(NavigationService navigationService, String previousPage, String nextPage){
        EssentialInfoGraphicControllerAlternative controller = new EssentialInfoGraphicControllerAlternative(navigationService, previousPage, nextPage);
        this.display(controller.getRoot(), "Inserisci Informazioni Essenziali");
    }

    public void navigateToCustomerView(NavigationService navigationService){
        CustomerViewControllerAlternative controller = new CustomerViewControllerAlternative(navigationService);
        this.display(controller.getRoot(), "Tutte le prenotazioni associate a te");
    }

    public void navigateToReceptionistView(NavigationService navigationService){
        ReceptionistViewControllerAlternative controller = new ReceptionistViewControllerAlternative(navigationService);
        this.display(controller.getRoot(), "Dati di tutte le prenotazioni");
    }

}
