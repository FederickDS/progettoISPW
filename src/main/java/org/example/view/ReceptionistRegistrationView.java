package org.example.view;

import javafx.scene.control.TextField;

import java.time.LocalDate;

public class ReceptionistRegistrationView extends AbstractRegistrationView {

    public ReceptionistRegistrationView() {
        super();
    }

    private boolean isValidDate(String date) {
        try {
            LocalDate.parse(date); // Verifica se il formato è "yyyy-MM-dd"
            return true;
        } catch (Exception e) {
            return false;
        }
    }


    @Override
    protected void addSpecificFields() {}

    @Override
    protected String getTitleText() {
        return "Registrati come Receptionist";
    }
    @Override
    public String getType(){
        return "receptionist";
    }

}
