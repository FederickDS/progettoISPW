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
        assertTrue(clientDao instanceof ClientDaoMemory, "Dovrebbe restituire ClientDaoMemory in modalità memory");
    }

    @Test
    void testGetReceptionistDao_Memory() {
        DaoFactory.setStorageOption("memory");
        GenericDao<Receptionist> receptionistDao = daoFactory.getReceptionistDao();
        assertTrue(receptionistDao instanceof ReceptionistDaoMemory, "Dovrebbe restituire ReceptionistDaoMemory in modalità memory");
    }

    @Test
    void testGetActivityDao_Memory() {
        DaoFactory.setStorageOption("memory");
        GenericDao<Activity> activityDao = daoFactory.getActivityDao();
        assertTrue(activityDao instanceof ActivityDaoMemory, "Dovrebbe restituire ActivityDaoMemory in modalità memory");
    }

    @Test
    void testGetRoomDao_Memory() {
        DaoFactory.setStorageOption("memory");
        GenericDao<Room> roomDao = DaoFactory.getRoomDao();
        assertTrue(roomDao instanceof RoomDaoMemory, "Dovrebbe restituire RoomDaoMemory in modalità memory");
    }

    @Test
    void testGetReservationDao_Memory() {
        DaoFactory.setStorageOption("memory");
        GenericDao<Reservation> reservationDao = DaoFactory.getReservationDao();
        assertTrue(reservationDao instanceof ReservationDaoMemory, "Dovrebbe restituire ReservationDaoMemory in modalità memory");
    }
}
