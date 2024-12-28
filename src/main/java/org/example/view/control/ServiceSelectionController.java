package org.example.view.control;

import org.example.entity.StartupSettingsEntity;
import org.example.factory.GUIFactory;
import org.example.view.ServiceSelection;
import javafx.stage.Stage;

import java.util.logging.Logger;

public class ServiceSelectionController {
    private ServiceSelection serviceSelection;
    private final Logger logger = Logger.getLogger(getClass().getName());
    private final Stage stage;

    public ServiceSelectionController(Stage stage) {
        this.stage = stage;
    }

    public void loadServiceSelection() {
        // Determina quale view caricare in base alle impostazioni
        GUIFactory factory = StartupSettingsEntity.getInstance().typeOfGUI();

        serviceSelection = factory.createServiceSelection();
        // Inizializza la view
        initController(this.stage);
    }

    private void initController(Stage stage) {
        // Mostra la view
        serviceSelection.display(stage);

        // Aggiungi gestione eventi per i bottoni
        addEventHandlers();
    }

    private void addEventHandlers() {
        // Bottone Conferma
        serviceSelection.getConfirmButton().setOnAction(e -> handleConfirm());

        // Bottone Annulla
        serviceSelection.getCancelButton().setOnAction(e -> handleCancel());
    }

    private void handleConfirm() {
        logger.info("Scelte confermate. Procedi con le azioni successive.");
        // Aggiungi logica per salvare le scelte o passare alla prossima view
        // Per ora, stampa le informazioni di debug
    }

    private void handleCancel() {
        logger.info("Scelte annullate. Torna alla Home Page.");
        NavigationManager navigationManager = new NavigationManager(stage);
        navigationManager.navigateToHomePage();
    }
}
