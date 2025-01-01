package org.example.entity;

import java.io.Serializable;
import java.time.LocalDateTime;

// Classe che rappresenta un cliente
class Client implements Serializable {
    private String firstName;
    private String lastName;
    private LocalDateTime birthDate;
    private String taxCode;
    private String phoneNumber;
    private String email;
    private String password;

    public Client() {
    }

    public Client(String firstName, String lastName, LocalDateTime birthDate, String taxCode, String phoneNumber, String email, String password) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.birthDate = birthDate;
        this.taxCode = taxCode;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.password = password;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public LocalDateTime getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(LocalDateTime birthDate) {
        this.birthDate = birthDate;
    }

    public String getTaxCode() {
        return taxCode;
    }

    public void setTaxCode(String taxCode) {
        this.taxCode = taxCode;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}