package org.example.graphic_controller;

import javafx.scene.layout.VBox;
import org.example.dao.DaoFactory;
import org.example.view.StartupSettingsView;

public class StartupSettingsController {
    private final StartupSettingsView view;
    private NavigationService navigationService;

    public StartupSettingsController(NavigationService navigationService) {
        this.navigationService = navigationService;
        this.view = new StartupSettingsView();
        view.getConfirmButton().setOnAction(e -> handleConfirmButton());
    }

    public VBox getRoot() {
        return view.getRoot();
    }

    private void handleConfirmButton() {
        String storageOption;
        String interfaceOption;
        String interfaceType;

        // Verifica della selezione della modalità di memorizzazione
        if (view.getInternalMemoryOption().isSelected()) {
            storageOption = "stateless";
        } else if (view.getDatabaseOption().isSelected()) {
            storageOption = "database";
        } else if (view.getFileOption().isSelected()) {
            storageOption = "file";
        } else {
            view.showOptionUnselected();
            return;
        }

        // Verifica della selezione della modalità dell'interfaccia
        if (view.getColorInterfaceOption().isSelected()) {
            interfaceOption = "color";
        } else if (view.getBwInterfaceOption().isSelected()) {
            interfaceOption = "BW";
        } else {
            view.showInterfaceUnselected();
            return;
        }

        // Verifica della selezione del tipo di interfaccia
        if (view.getMainInterfaceOption().isSelected()) {
            interfaceType = "main";
        } else if (view.getSecondaryInterfaceOption().isSelected()) {
            interfaceType = "secondary";
        } else {
            view.showInterfaceTypeUnselected();
            return;
        }

        if ("secondary".equals(interfaceType)) {
            navigationService = new NavigationManagerAlternative(navigationService.getStage());
        }

        // Salvataggio interfaccia
        navigationService.setInterfaceOption(interfaceOption);
        // Salvataggio opzione di persistenza
        DaoFactory.setStorageOption(storageOption);

        navigationService.navigateToHomePage(this.navigationService);
    }
}
