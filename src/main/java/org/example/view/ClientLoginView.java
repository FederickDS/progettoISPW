package org.example.view;

import javafx.stage.Stage;

public class ClientLoginView extends AbstractLoginView {

    public ClientLoginView(Stage stage) {
        super(stage);
    }

    @Override
    protected String getTitleText() {
        return "Login come Cliente";
    }
    @Override
    public String getType(){
        return "client";
    }
}
