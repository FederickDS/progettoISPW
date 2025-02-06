package org.example.bean;

import org.example.view.AbstractLoginView;
import org.example.view.AbstractLoginAlternativeView;

import java.util.regex.Pattern;

public class LoginBean {
    private String email;
    private String password;
    private String userType;
    private static final String EMAIL_REGEX = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";

    public LoginBean() {
        // Costruttore vuoto per inizializzazione graduale
    }

    public void populateFromView(AbstractLoginView loginView) {
        this.email = loginView.getEmailField().getText();
        this.password = loginView.getPasswordField().getText();
        this.userType = loginView.getType();
    }

    public void populateFromAlternativeView(AbstractLoginAlternativeView loginView) {
        this.email = loginView.getEmailField().getText();
        this.password = loginView.getPasswordField().getText();

        // Determina il tipo di login in base alla selezione dei RadioButton
        if (loginView.getClientLoginOption().isSelected()) {
            this.userType = "client";
        } else if (loginView.getReceptionistLoginOption().isSelected()) {
            this.userType = "receptionist";
        } else {
            this.userType = null;
        }
    }

    public boolean validateFields(AbstractLoginView loginView) {
        boolean valid = true;
        loginView.hideErrorMessages();
        if (email.isBlank() || !Pattern.matches(EMAIL_REGEX, email)) {
            loginView.showEmailError();
            valid = false;
        }
        if (password.isBlank()) {
            loginView.showPasswordError();
            valid = false;
        }
        return valid;
    }

    public boolean validateFields(AbstractLoginAlternativeView loginView) {
        boolean valid = true;
        loginView.hideErrorMessages();

        if (email.isBlank() || !Pattern.matches(EMAIL_REGEX, email)) {
            loginView.showEmailError();
            valid = false;
        }
        if (password.isBlank()) {
            loginView.showPasswordError();
            valid = false;
        }
        if (userType == null) {
            loginView.getErrorMessage().setText("Seleziona un tipo di accesso.");
            loginView.getErrorMessage().setVisible(true);
            valid = false;
        }
        return valid;
    }

    // Getters
    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getUserType() {
        return userType;
    }

    // Setters
    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }
}
