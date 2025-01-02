package org.example.view;

import javafx.stage.Stage;

public class ReceptionistLoginView extends AbstractLoginView {

    public ReceptionistLoginView(Stage stage) {
        super(stage);
    }

    @Override
    protected String getTitleText() {
        return "Login come Receptionist";
    }
}
