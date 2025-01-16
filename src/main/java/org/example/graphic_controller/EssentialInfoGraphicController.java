package org.example.graphic_controller;

import javafx.scene.layout.VBox;
import org.example.application_controller.EssentialInfoController;
import org.example.entity.Client;
import org.example.entity.User;
import org.example.view.EssentialInfoView;

import javax.swing.text.View;
import java.util.logging.Logger;

public class EssentialInfoGraphicController {
    private static final Logger logger = Logger.getLogger(EssentialInfoGraphicController.class.getName());
    private EssentialInfoView essentialInfoView;
    private final String previousPage;
    private final String nextPage;
    private final NavigationService navigationService;

    public EssentialInfoGraphicController(NavigationService navigationService, String previousPage, String nextPage) {
        this.navigationService = navigationService;
        this.previousPage = previousPage;
        this.nextPage = nextPage;
        this.essentialInfoView = new EssentialInfoView();
        essentialInfoView.hideBasicFields();
        addEventHandlers();
    }

    private void addEventHandlers() {
        essentialInfoView.getRegisterButton().setOnAction(e -> handleRegistration());
        essentialInfoView.getCancelButton().setOnAction(e -> navigateBack());
    }

    private void handleRegistration() {
        EssentialInfoController appController = new EssentialInfoController();

        // Controllo dei campi prima di procedere
        if (!appController.checkFields(essentialInfoView)) {
            return;
        }

        Client client = appController.createClientFromInput(essentialInfoView);

        String result = appController.registerClient(client);
        if ("success".equals(result)) {
            navigateToNextPage(client);
        } else {
            essentialInfoView.showDatabaseError("Errore durante la registrazione. Riprova.");
        }
    }

    private void navigateToNextPage(User user) {
        // Passa l'utente autenticato alla pagina successiva o memorizzalo in una sessione
        if (nextPage.equalsIgnoreCase("homepage")) {
            navigationService.navigateToHomePage(this.navigationService);
        } else if (nextPage.equalsIgnoreCase("ServiceSelection")) {
            navigationService.navigateToServiceSelection(this.navigationService, user);
        }
    }

    private void navigateBack() {
        if (previousPage == null || previousPage.isBlank()) {
            logger.warning("Pagina precedente non definita. Operazione annullata.");
            return;
        }

        switch (previousPage) {
            case "StartupSettings" -> navigationService.navigateToStartupSettings(this.navigationService);
            case "HomePage" -> navigationService.navigateToHomePage(this.navigationService);
            case "RoomBookingOptions" -> navigationService.navigateToRoomBookingOptions(this.navigationService);
            default -> logger.warning("Pagina precedente sconosciuta");
        }
    }

    public EssentialInfoView getView() {
        return essentialInfoView;
    }
}
