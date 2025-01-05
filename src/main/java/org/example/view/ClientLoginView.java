package org.example.view;

public class ClientLoginView extends AbstractLoginView {

    public ClientLoginView() {
        //non serve, titolo grazie a metodo getTitleText
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
