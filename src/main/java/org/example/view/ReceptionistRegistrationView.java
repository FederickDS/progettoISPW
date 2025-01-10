package org.example.view;

import java.time.LocalDate;

public class ReceptionistRegistrationView extends AbstractRegistrationView {

    public ReceptionistRegistrationView() {
        super();
    }

    @Override
    protected void addSpecificFields() {/*non implementato, solo da client, receptionist non ha altri campi*/}

    @Override
    protected String getTitleText() {
        return "Registrati come Receptionist";
    }
    @Override
    public String getType(){
        return "receptionist";
    }

}
