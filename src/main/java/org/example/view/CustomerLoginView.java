package org.example.view;

import javafx.stage.Stage;

public class CustomerLoginView extends AbstractLoginView {

    public CustomerLoginView(Stage stage) {
        super(stage);
    }

    @Override
    protected String getTitleText() {
        return "Login come Cliente";
    }
    @Override
    protected String getType(){
        return "client";
    }
}
