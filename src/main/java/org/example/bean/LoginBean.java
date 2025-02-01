package org.example.bean;

import org.example.view.AbstractLoginView;

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
