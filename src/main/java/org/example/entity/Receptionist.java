package org.example.entity;

public class Receptionist extends User {
    private String employeeId;

    public Receptionist() {
    }

    public String getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(String employeeId) {
        this.employeeId = employeeId;
    }

    @Override
    public String getUserType() {
        return "receptionist";
    }
}
