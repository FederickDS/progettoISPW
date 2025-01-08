package org.example.entity;

public class Receptionist extends User {

    public Receptionist() {}
    public Receptionist(String firstName, String lastName, String email, String phoneNumber, String password) {
        super(firstName, lastName, email, phoneNumber, password);
    }
    @Override
    public String getUserType() {
        return "receptionist";
    }
}
