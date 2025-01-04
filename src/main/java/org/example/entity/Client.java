package org.example.entity;

import java.time.LocalDateTime;

public class Client extends User {
    private LocalDateTime birthDate;
    private String taxCode;
    private String phoneNumber;

    public Client() {/*classe bean*/}

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

    @Override
    public String getUserType() {
        return "client";
    }
}
