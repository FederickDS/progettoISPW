package org.example.graphic_controller;

import javafx.stage.Stage;
import org.example.app_controller.ValidateLogin;
import org.example.view.AbstractLoginView;
import org.example.view.ClientLoginView;
import org.example.view.ReceptionistLoginView;

import java.util.logging.Logger;

public class LoginController {
    private final Logger logger = Logger.getLogger(getClass().getName());
    private final Stage stage;
    private final NavigationService navigationService;
    private AbstractLoginView loginView;
    private final String previousPage;
    private final String nextPage;

    public LoginController(Stage stage, NavigationService navigationService, String previousPage, String nextPage) {
        this.stage = stage;
        this.navigationService = navigationService;
        this.previousPage = previousPage;
        this.nextPage = nextPage;
    }

    public void loadLoginView(String typeOfLogin) {
        if(typeOfLogin.equalsIgnoreCase("client")){
            this.loginView = new ClientLoginView();
        }else{
            this.loginView = new ReceptionistLoginView();
        }

        // Mostra la view
        navigationService.display(stage, loginView.getVBox(), "Login");

        // Aggiungi gestione eventi
        addEventHandlers();
    }

    private void addEventHandlers() {
        // Bottone di Login
        loginView.getLoginButton().setOnAction(e -> handleLogin());

        // Gestione navigazione indietro
        loginView.getCancelButton().setOnAction(e -> navigateBack());

        loginView.getRegisterButton().setOnAction(e -> goToRegistration());
    }

    private void handleLogin() {
        String username = loginView.getUsernameField().getText();
        String password = loginView.getPasswordField().getText();

        if (username.isBlank() || password.isBlank()) {
            loginView.getErrorMessage().setVisible(true);
            loginView.getErrorMessage().setManaged(true);
            // Mostra un messaggio di errore nella view (aggiungibile nella tua classe `AbstractLoginView`).
            return;
        }
        ValidateLogin validateLogin = new ValidateLogin(this.loginView.getType());

        if (validateLogin.validate(username,password)) {
            logger.info("Login riuscito!");
            navigateToNextPage();
        } else {
            logger.warning("Login fallito. Credenziali non valide.");
            // Mostra un messaggio di errore nella view.
        }
    }

    private void navigateToNextPage() {
        if(nextPage.equalsIgnoreCase("homepage")){
            navigationService.navigateToHomePage();
        }else if(nextPage.equalsIgnoreCase("serviceSelection")){
            navigationService.navigateToServiceSelection();
        }
    }

    private void navigateBack() {
        if (previousPage == null || previousPage.isBlank()) {
            logger.warning("Pagina precedente non definita. Operazione annullata.");
            return;
        }

        // Utilizza il NavigationService direttamente per navigare indietro
        if(previousPage.equalsIgnoreCase("HomePage")){
            navigationService.navigateToHomePage();
        }else if(previousPage.equalsIgnoreCase("ServiceSelection")){
            navigationService.navigateToServiceSelection();
        }else if(previousPage.equalsIgnoreCase("StartupSettings")){
            navigationService.navigateToStartupSettings();
        }
    }

    private void goToRegistration() {
        navigationService.navigateToRegistration(previousPage,nextPage,loginView.getType());
    }
}
