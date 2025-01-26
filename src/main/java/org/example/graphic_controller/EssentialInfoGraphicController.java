package org.example.graphic_controller;

import javafx.scene.layout.VBox;
import org.example.application_controller.EssentialInfoController;
import org.example.entity.Client;
import org.example.view.EssentialInfoView;

public class EssentialInfoGraphicController {
    private final EssentialInfoView essentialInfoView;
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
        essentialInfoView.getCancelButton().setOnAction(e -> this.navigationService.navigateBack(previousPage,this.navigationService));
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
            //salvo le credenziali (sicuramente corrette dopo register user)
            SessionManager.getInstance().setCredentials(essentialInfoView.getEmailField().getText(),essentialInfoView.getPasswordField().getText(),essentialInfoView.getType());
            navigateToNextPage();
        } else {
            essentialInfoView.showDatabaseError("Errore durante la registrazione. Riprova.");
        }
    }

    private void navigateToNextPage() {
        // Passa l'utente autenticato alla pagina successiva o memorizzalo in una sessione
        if (nextPage.equalsIgnoreCase("HomePage")) {
            navigationService.navigateToHomePage(this.navigationService);
        } else if (nextPage.equalsIgnoreCase("ServiceSelection")) {
            navigationService.navigateToServiceSelection(this.navigationService);
        }
    }

    public VBox getRoot() {
        return essentialInfoView.getRoot();
    }
}
