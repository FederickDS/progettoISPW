package org.example.view.control;

import javafx.stage.Stage;
import org.example.control.ValidateLogin;
import org.example.view.AbstractLoginView;
import org.example.view.CustomerLoginView;
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
            this.loginView = new CustomerLoginView(this.stage);
        }else{
            this.loginView = new ReceptionistLoginView(this.stage);
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
        if(previousPage.equalsIgnoreCase("homepage")){
            navigationService.navigateToHomePage();
        }else if(previousPage.equalsIgnoreCase("serviceSelection")){
            navigationService.navigateToServiceSelection();
        }else if(previousPage.equalsIgnoreCase("startupSettings")){
            navigationService.navigateToStartupSettings();
        }
    }
}
