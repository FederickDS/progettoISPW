package org.example.view;

import javafx.scene.control.TextField;

public class ReceptionistRegistrationView extends AbstractRegistrationView {
    private TextField employeeIdField;

    public ReceptionistRegistrationView() {
        super();
    }

    @Override
    protected void addSpecificFields() {
        // Campo specifico per i receptionist
        employeeIdField = new TextField();
        employeeIdField.setPromptText("ID Dipendente");

        // Aggiungi campo specifico al layout
        root.getChildren().add(employeeIdField);
    }

    @Override
    protected String getTitleText() {
        return "Registrati come Receptionist";
    }
    @Override
    public String getType(){
        return "receptionist";
    }

    public TextField getEmployeeIdField() {
        return employeeIdField;
    }
}
