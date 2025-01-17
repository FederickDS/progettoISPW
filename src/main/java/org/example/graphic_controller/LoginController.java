package org.example.graphic_controller;

import javafx.scene.layout.VBox;
import org.example.application_controller.ValidateLogin;
import org.example.entity.User;
import org.example.view.AbstractLoginView;
import org.example.view.ClientLoginView;
import org.example.view.ReceptionistLoginView;

import java.util.logging.Logger;

public class LoginController {
    private final Logger logger = Logger.getLogger(getClass().getName());
    private AbstractLoginView loginView;
    private final String previousPage;
    private final String nextPage;
    private final NavigationService navigationService;

    public LoginController(NavigationService navigationService, String previousPage, String nextPage, String typeOfLogin) {
        this.navigationService = navigationService;
        this.previousPage = previousPage;
        this.nextPage = nextPage;
        //tipo di view
        if(typeOfLogin.equalsIgnoreCase("client")){
            this.loginView = new ClientLoginView();
        }else{
            this.loginView = new ReceptionistLoginView();
        }
        // Aggiungi gestione eventi
        addEventHandlers();
    }

    private void addEventHandlers() {
        // Bottone di Login
        loginView.getLoginButton().setOnAction(e -> handleLogin());

        // Gestione navigazione indietro
        loginView.getCancelButton().setOnAction(e -> this.navigationService.navigateBack(previousPage,this.navigationService));

        loginView.getRegisterButton().setOnAction(e -> goToRegistration());
    }

    private void handleLogin() {
        String email = loginView.getEmailField().getText();
        String password = loginView.getPasswordField().getText();

        if (email.isBlank() || password.isBlank()) {
            loginView.getErrorMessage().setVisible(true);
            loginView.getErrorMessage().setManaged(true);
            return;
        }

        ValidateLogin validateLogin = new ValidateLogin();
        String typeOfLogin = loginView.getType();
        try {
            User user = validateLogin.validate(email, navigationService.hashWithSHA256(password), typeOfLogin);
            if (user != null) {
                logger.info("Login riuscito!");
                navigateToNextPage(user);
            } else {
                logger.warning("Login fallito. Credenziali non valide.");
                loginView.getErrorMessage().setVisible(true);
                loginView.getErrorMessage().setManaged(true);
            }
        } catch (RuntimeException e) {
            logger.info(e.getMessage());
        }
    }

    private void navigateToNextPage(User user) {
        // Passa l'utente autenticato alla pagina successiva o memorizzalo in una sessione
        if (nextPage.equalsIgnoreCase("homepage")) {
            navigationService.navigateToHomePage(this.navigationService);
        } else if (nextPage.equalsIgnoreCase("serviceSelection")) {
            navigationService.navigateToServiceSelection(this.navigationService, user);
        }
    }

    private void goToRegistration() {
        navigationService.navigateToRegistration(this.navigationService,previousPage,nextPage,loginView.getType());
    }

    public VBox getView(){
        return this.loginView.getRoot();
    }
}
