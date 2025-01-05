package org.example.view;

public class ReceptionistLoginView extends AbstractLoginView {

    public ReceptionistLoginView() {
        //non serve, titolo grazie a metodo getTitleText
    }

    @Override
    protected String getTitleText() {
        return "Login come Receptionist";
    }
    @Override
    public String getType(){
        return "receptionist";
    }
}
