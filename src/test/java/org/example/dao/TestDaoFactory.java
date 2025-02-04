package org.example.dao;

import static org.junit.jupiter.api.Assertions.*;

import org.example.entity.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.sql.Connection;

class TestDaoFactory {
    private DaoFactory daoFactory;

    @Mock
    private DatabaseConnectionManager mockConnectionManager;

    @Mock
    private Connection mockConnection;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        daoFactory = new DaoFactory();
    }

    @Test
    void testGetClientDao_Memory() {
        DaoFactory.setStorageOption("memory");
        GenericDao<Client> clientDao = daoFactory.getClientDao();
        assertInstanceOf(ClientDaoMemory.class, clientDao, "Dovrebbe restituire ClientDao in modalità memory");
    }

    @Test
    void testGetReceptionistDao_Memory() {
        DaoFactory.setStorageOption("memory");
        GenericDao<Receptionist> receptionistDao = daoFactory.getReceptionistDao();
        assertInstanceOf(ReceptionistDaoMemory.class, receptionistDao, "Dovrebbe restituire ReceptionistDaoM in modalità memory");
    }

    @Test
    void testGetActivityDao_Memory() {
        DaoFactory.setStorageOption("memory");
        GenericDao<Activity> activityDao = daoFactory.getActivityDao();
        assertInstanceOf(ActivityDaoMemory.class, activityDao, "Dovrebbe restituire ActivityDao in modalità memory");
    }

    @Test
    void testGetRoomDao_Memory() {
        DaoFactory.setStorageOption("memory");
        GenericDao<Room> roomDao = DaoFactory.getRoomDao();
        assertInstanceOf(RoomDaoMemory.class, roomDao, "Dovrebbe restituire RoomDao in modalità memory");
    }

    @Test
    void testGetReservationDao_Memory() {
        DaoFactory.setStorageOption("memory");
        GenericDao<Reservation> reservationDao = DaoFactory.getReservationDao();
        assertInstanceOf(ReservationDaoMemory.class, reservationDao, "Dovrebbe restituire ReservationDao in modalità memory");
    }
}
