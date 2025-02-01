package org.example.facade;

import org.example.bean.LoginBean;
import org.example.entity.Reservation;
import org.example.entity.User;

public interface ApplicationFacadeInterface {
    /**
     * Ottiene il numero massimo di partecipanti consentito nelle stanze disponibili.
     * @return Il numero massimo di partecipanti.
     */
    int getMaxNumberOfParticipants();
}
