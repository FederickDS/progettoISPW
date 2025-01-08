package org.example.entity;

import java.sql.Date;
import java.time.LocalDate;

public class Client extends User {
    private LocalDate birthDate;
    private String taxCode;

    public Client() {/*classe bean*/}

    public Client(String firstName, String lastName, String email, String phoneNumber, String password, LocalDate birthDate, String taxCode) {
        super(firstName, lastName, email, phoneNumber, password);
        this.birthDate = birthDate;
        this.taxCode = taxCode;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }

    public String getTaxCode() {
        return taxCode;
    }

    public void setTaxCode(String taxCode) {
        this.taxCode = taxCode;
    }

    @Override
    public String getUserType() {
        return "client";
    }
}
