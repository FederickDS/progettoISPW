package org.example.bean;

import org.example.view.AbstractRegistrationView;
import org.example.view.ClientRegistrationView;
import org.example.view.ReceptionistRegistrationView;
import org.example.view.RegistrationViewAlternative;

import java.time.LocalDate;
import java.util.regex.Pattern;

public class UserRegistrationBean {
    private String firstName;
    private String lastName;
    private String email;
    private String phoneNumber;
    private String password;
    private String repeatPassword;
    private String taxCode;
    private LocalDate birthDate;
    private String userType;

    private static final String EMAIL_REGEX = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";

    public UserRegistrationBean(){
        //classe bean
    }

    public UserRegistrationBean(AbstractRegistrationView registrationView) {
        this.firstName = registrationView.getFirstNameField().getText();
        this.lastName = registrationView.getLastNameField().getText();
        this.email = registrationView.getEmailField().getText();
        this.phoneNumber = registrationView.getPhoneNumberField().getText();
        this.password = registrationView.getPasswordField().getText();
        this.repeatPassword = registrationView.getRepeatPasswordField().getText();

        if (registrationView instanceof ClientRegistrationView clientView) {
            this.userType = "client";
            this.taxCode = clientView.getTaxCodeField().getText();
            try {
                this.birthDate = LocalDate.parse(clientView.getBirthDateField().getText());
            } catch (Exception e) {
                this.birthDate = null; // Indica un formato errato
            }
        } else if (registrationView instanceof ReceptionistRegistrationView) {
            this.userType = "receptionist";
        }
    }

    public UserRegistrationBean(RegistrationViewAlternative registrationView) {
        this.firstName = registrationView.getFirstNameField().getText();
        this.lastName = registrationView.getLastNameField().getText();
        this.email = registrationView.getEmailField().getText();
        this.phoneNumber = registrationView.getPhoneNumberField().getText();
        this.password = registrationView.getPasswordField().getText();
        this.repeatPassword = registrationView.getRepeatPasswordField().getText();
        this.userType = registrationView.getSelectedUserType();
    }

    public boolean validateAllFields(RegistrationViewAlternative registrationView) {
        boolean valid = true;

        // Reset visibilità errori
        registrationView.getFirstNameError().setVisible(false);
        registrationView.getLastNameError().setVisible(false);
        registrationView.getEmailError().setVisible(false);
        registrationView.getPhoneNumberError().setVisible(false);
        registrationView.getPasswordError().setVisible(false);
        registrationView.getRepeatPasswordError().setVisible(false);

        // Controllo Nome
        if (firstName.isBlank()) {
            registrationView.getFirstNameError().setVisible(true);
            valid = false;
        }

        // Controllo Cognome
        if (lastName.isBlank()) {
            registrationView.getLastNameError().setVisible(true);
            valid = false;
        }

        // Controllo Email
        if (email.isBlank() || !Pattern.matches(EMAIL_REGEX, email)) {
            registrationView.getEmailError().setVisible(true);
            valid = false;
        }

        // Controllo Numero di Telefono
        if (phoneNumber.length() != 10) {
            registrationView.getPhoneNumberError().setVisible(true);
            valid = false;
        }

        // Controllo Password
        if (password.isBlank() || password.length() < 8 || password.length() > 16) {
            registrationView.getPasswordError().setText("Inserire una password tra gli 8 e i 16 caratteri");
            registrationView.getPasswordError().setVisible(true);
            valid = false;
        }

        // Controllo Ripetizione Password
        if (!password.equals(repeatPassword)) {
            registrationView.getRepeatPasswordError().setText("Le password non coincidono");
            registrationView.getRepeatPasswordError().setVisible(true);
            valid = false;
        }

        return valid;
    }


    public boolean validateBasicFields(AbstractRegistrationView registrationView) {
        boolean valid = true;
        registrationView.hideAllErrors();

        if (firstName.isBlank()) {
            registrationView.showFirstNameError();
            valid = false;
        }
        if (lastName.isBlank()) {
            registrationView.showLastNameError();
            valid = false;
        }
        if (email.isBlank() || !Pattern.matches(EMAIL_REGEX, email)) {
            registrationView.showEmailError();
            valid = false;
        }
        if (password.isBlank() || password.length() < 8 || password.length() > 16) {
            registrationView.showPasswordError("Inserire una password tra gli 8 e i 16 caratteri");
            valid = false;
        }
        if (!password.equals(repeatPassword)) {
            registrationView.showRepeatPasswordError("Non hai ripetuto la stessa password");
            valid = false;
        }
        if (phoneNumber.length() != 10) {
            registrationView.showPhoneNumberError("Il numero di telefono deve essere di 10 caratteri, senza prefisso, italiano.");
            valid = false;
        }
        return valid;
    }

    public boolean validateSpecificFields(AbstractRegistrationView registrationView) {
        boolean valid = true;
        registrationView.hideSpecificErrors();

        if (registrationView instanceof ClientRegistrationView clientView) {
            if (taxCode.length() != 16) {
                clientView.showTaxCodeError("Inserire un codice fiscale valido.");
                valid = false;
            }
            if (birthDate == null) {
                clientView.showBirthDateError("Formato data non corretto (YYYY-MM-DD).");
                valid = false;
            }
        }
        return valid;
    }

    //la view serve solo a mostrare i messaggi di errore nell'interfaccia
    public boolean validateAllFields(AbstractRegistrationView registrationView) {
        return validateBasicFields(registrationView) && validateSpecificFields(registrationView);
    }

    // Getters
    public String getFirstName() { return firstName; }
    public String getLastName() { return lastName; }
    public String getEmail() { return email; }
    public String getPhoneNumber() { return phoneNumber; }
    public String getPassword() { return password; }
    public String getUserType() { return userType; }
    public String getTaxCode() { return taxCode; }
    public LocalDate getBirthDate() { return birthDate; }
    //Setters
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public void setUserType(String userType) {
        this.userType = userType;
    }
    public void setTaxCode(String taxCode) {
        this.taxCode = taxCode;
    }
    public void setBirthDate(String birthDate) {
        this.birthDate = LocalDate.parse(birthDate);
    }
}
