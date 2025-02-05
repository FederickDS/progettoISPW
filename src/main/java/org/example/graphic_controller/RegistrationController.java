package org.example.graphic_controller;

import javafx.scene.layout.VBox;
import org.example.exception.UserAlreadyInsertedException;
import org.example.exception.HashingException;
import org.example.facade.ApplicationFacade;
import org.example.factory.ModelBeanFactory;
import org.example.bean.UserRegistrationBean;
import org.example.view.AbstractRegistrationView;
import org.example.view.ClientRegistrationView;
import org.example.view.ReceptionistRegistrationView;
import org.example.application_controller.UserRegistrationController;

import java.util.logging.Logger;

public class RegistrationController {
    private final Logger logger = Logger.getLogger(getClass().getName());
    private AbstractRegistrationView registrationView;
    private final String previousPage;
    private final String nextPage;
    private final NavigationService navigationService;

    public RegistrationController(NavigationService navigationService, String previousPage, String nextPage, String userType) {
        this.navigationService = navigationService;
        this.previousPage = previousPage;
        this.nextPage = nextPage;
        //meccanismo di scelta
        if (userType.equalsIgnoreCase("client")) {
            this.registrationView = new ClientRegistrationView();
        } else {
            this.registrationView = new ReceptionistRegistrationView();
        }
        // Aggiungi gestione eventi
        addEventHandlers();
    }

    private void addEventHandlers() {
        // Bottone di Registrazione
        registrationView.getRegisterButton().setOnAction(e -> handleRegistration());

        // Gestione navigazione indietro
        registrationView.getCancelButton().setOnAction(e -> this.navigationService.navigateBack(previousPage,this.navigationService));

        registrationView.getLoginButton().setOnAction(e -> goToLogin());
    }

    private void handleRegistration() {
        UserRegistrationController appController = new UserRegistrationController();
        UserRegistrationBean userRegistrationBean = ModelBeanFactory.getUserRegistrationBean(registrationView);

        if (!userRegistrationBean.validateAllFields(registrationView)) {
            return;
        }

        try {
            String result = appController.registerUser(userRegistrationBean);

            switch (result) {
                case "success" -> navigateToNextPage();
                case "error:phone_exists" -> registrationView.showPhoneNumberError("Il numero di telefono è già registrato.");
                case "error:database_error" -> registrationView.showDatabaseError("Errore durante la registrazione. Riprova più tardi.");
                default -> registrationView.showDatabaseError("Errore sconosciuto.");
            }
        } catch (HashingException e) {
            logger.severe("Errore durante l'hashing della password: " + e.getMessage());
            ApplicationFacade.showErrorMessage("Errore di registrazione","Errore durante la registrazione","Errore di sistema durante la registrazione. Riprova più tardi.");
        } catch (UserAlreadyInsertedException e){
            logger.severe("Errore durante il salvataggio dell'utente: " + e.getMessage());
            ApplicationFacade.showErrorMessage("Dati duplicati","Controlla i campi inseriti",e.getMessage());
        }
    }

    private void navigateToNextPage() {
        //salvo le credenziali (sicuramente corrette dopo register user)
        switch (nextPage) {
            case "HomePage" -> navigationService.navigateToHomePage(this.navigationService);
            case "ServiceSelection" -> navigationService.navigateToServiceSelection(this.navigationService);
            default -> logger.warning("Pagina successiva non definita");
        }
    }

    private void goToLogin() {
        navigationService.navigateToLogin(this.navigationService, previousPage,nextPage,registrationView.getType());
    }

    public VBox getRoot(){
        return this.registrationView.getRoot();
    }
}
