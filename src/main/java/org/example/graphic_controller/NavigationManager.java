package org.example.graphic_controller;

import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.logging.Logger;
import org.example.application_controller.BookRoom;

import javafx.scene.layout.VBox;

public class NavigationManager implements NavigationService {
    private String interfaceOption = "color";
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
    public void navigateToHomePageAlternative(NavigationService navigationService) {
        HomePageAlternativeController controller = new HomePageAlternativeController(navigationService);
        this.display(controller.getRoot(), "Home Page (Alternativa)");
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

    public void navigateToReservationPayment(NavigationService navigationService, BookRoom bookRoom) {
        ReservationPaymentController controller = new ReservationPaymentController(navigationService, bookRoom);
        this.display(controller.getRoot(), "Pagamento prenotazione");
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

    @Override
    public void navigateToCustomerView(NavigationService navigationService){
        CustomerViewController controller = new CustomerViewController(navigationService);
        this.display(controller.getRoot(), "Tutte le prenotazioni associate a te");
    }

    public void navigateToReceptionistView(NavigationService navigationService){
        ReceptionistViewController controller = new ReceptionistViewController(navigationService);
        this.display(controller.getRoot(), "Dati di tutte le prenotazioni");
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

    public void navigateBack(String previousPage, NavigationService navigationService) {
        if (previousPage == null || previousPage.isBlank()) {
            logger.warning("Pagina precedente non definita. Operazione annullata.");
            return;
        }

        switch (previousPage) {
            case "StartupSettings" -> navigationService.navigateToStartupSettings(navigationService);
            case "HomePage" -> navigationService.navigateToHomePage(navigationService);
            case "HomePageAlternative" -> navigationService.navigateToHomePageAlternative(navigationService);
            case "NotImplemented" -> navigationService.navigateToNotImplemented(navigationService,previousPage);
            case "BookingRoomController" -> navigationService.navigateToBookingRoom(navigationService,null);
            default -> logger.warning("Pagina precedente sconosciuta");
        }
    }

    public void setInterfaceOption(String interfaceOption) {
        this.interfaceOption = interfaceOption;
    }

    public String getInterfaceOption() {
        return this.interfaceOption;
    }

    public String getCSSStyle(){
        String cssFile;
        if(this.interfaceOption.equals("color")){
            cssFile = "/style/color-mode.css";
        }else{
            cssFile = "/style/bw-mode.css";
        }
        return cssFile;
    }
}
