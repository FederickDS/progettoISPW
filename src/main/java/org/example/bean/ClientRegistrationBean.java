package org.example.bean;

import org.example.view.AbstractRegistrationView;

import java.io.Serializable;

public class ClientRegistrationBean implements Serializable {
    private String firstName;
    private String lastName;
    private String email;
    private String phoneNumber;
    private String password;
    private String birthDate;
    private String taxCode;

    public ClientRegistrationBean() {}

    public ClientRegistrationBean(String firstName, String lastName, String email, String phoneNumber, String password, String birthDate, String taxCode) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.password = password;
        this.birthDate = birthDate;
        this.taxCode = taxCode;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName, AbstractRegistrationView registrationView) {
        if(firstName.isBlank()){
            registrationView.showFirstNameError();
            return;
        }
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName, AbstractRegistrationView registrationView) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email, AbstractRegistrationView registrationView) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber, AbstractRegistrationView registrationView) {
        this.phoneNumber = phoneNumber;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password, AbstractRegistrationView registrationView) {
        this.password = password;
    }

    public String getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(String birthDate, AbstractRegistrationView registrationView) {
        this.birthDate = birthDate;
    }

    public String getTaxCode() {
        return taxCode;
    }

    public void setTaxCode(String taxCode, AbstractRegistrationView registrationView) {
        this.taxCode = taxCode;
    }
}
