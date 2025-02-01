package org.example.dao;

import org.example.entity.*;

import java.sql.Connection;
import java.util.List;

public class DaoFactory implements DaoFactoryInterface{
    private static String storageOption = "stateless";
    private static ClientDaoMemory clientDaoMemoryInstance;
    private static ReceptionistDaoMemory receptionistDaoMemoryInstance;
    private static GenericDao<Activity> activityDaoMemoryInstance;
    private static GenericDao<Service> serviceDaoMemoryInstance;
    private static GenericDao<DailyTimeInterval> timeIntervalDaoMemoryInstance; // Aggiunto per TimeInterval
    private static GenericDao<Room> roomDaoMemoryInstance;
    private static GenericDao<Reservation> reservationDaoMemoryInstance;

    private static final String DATABASE = "database";

    public DaoFactory() {
        // Costruttore privato per nascondere quello pubblico implicito
    }

    public GenericDao<Client> getClientDao() {
        if (DATABASE.equalsIgnoreCase(storageOption)) {
            Connection connection = DatabaseConnectionManager.getConnection();
            return new ClientDaoDB(connection);
        } else {
            if (clientDaoMemoryInstance == null) {
                clientDaoMemoryInstance = createClientMemoryInstance();
            }
            return clientDaoMemoryInstance;
        }
    }

    public static ClientDaoMemory createClientMemoryInstance(){
        return new ClientDaoMemory();
    }

    public static GenericDao<Receptionist> getReceptionistDao() {
        if (DATABASE.equalsIgnoreCase(storageOption)) {
            Connection connection = DatabaseConnectionManager.getConnection();
            return new ReceptionistDaoDB(connection);
        } else {
            if (receptionistDaoMemoryInstance == null) {
                receptionistDaoMemoryInstance = new ReceptionistDaoMemory();
            }
            return receptionistDaoMemoryInstance;
        }
    }

    public GenericDao<Activity> getActivityDao() {
        if (DATABASE.equalsIgnoreCase(storageOption)) {
            Connection connection = DatabaseConnectionManager.getConnection();
            return new ActivityDaoDB(connection);
        } else {
            if (activityDaoMemoryInstance == null) {
                activityDaoMemoryInstance = createActivityMemoryInstance();
            }
            return activityDaoMemoryInstance;
        }
    }

    public static ActivityDaoMemory createActivityMemoryInstance(){
        return new ActivityDaoMemory();
    }

    public GenericDao<Service> getServiceDao() {
        if (DATABASE.equalsIgnoreCase(storageOption)) {
            Connection connection = DatabaseConnectionManager.getConnection();
            return new ServiceDaoDB(connection);
        } else {
            if (serviceDaoMemoryInstance == null) {
                serviceDaoMemoryInstance = createServiceMemoryInstance();
            }
            return serviceDaoMemoryInstance;
        }
    }
    public static ServiceDaoMemory createServiceMemoryInstance(){
        return new ServiceDaoMemory();
    }

    public static GenericDao<DailyTimeInterval> getTimeIntervalDao() { // Metodo per TimeInterval
        if (DATABASE.equalsIgnoreCase(storageOption)) {
            Connection connection = DatabaseConnectionManager.getConnection();
            return new DailyTimeIntervalDaoDB(connection); // Implementazione per DB
        } else {
            if (timeIntervalDaoMemoryInstance == null) {
                timeIntervalDaoMemoryInstance = new DailyTimeIntervalDaoMemory(); // Implementazione per memoria
            }
            return timeIntervalDaoMemoryInstance;
        }
    }

    public static GenericDao<Room> getRoomDao() {
        if (DATABASE.equalsIgnoreCase(storageOption)) {
            Connection connection = DatabaseConnectionManager.getConnection();
            return new RoomDaoDB(connection);
        } else {
            if (roomDaoMemoryInstance == null) {
                roomDaoMemoryInstance = new RoomDaoMemory();
            }
            return roomDaoMemoryInstance;
        }
    }

    // Nuovi metodi per ottenere liste di attività e servizi
    public static List<Activity> getAvailableActivities() {
        DaoFactory daoFactory = new DaoFactory();
        GenericDao<Activity> activityDao = daoFactory.getActivityDao();
        return activityDao.readAll(); // Metodo da implementare nel DAO
    }

    public static List<Service> getAvailableServices() {
        DaoFactory daoFactory = new DaoFactory();
        GenericDao<Service> serviceDao = daoFactory.getServiceDao();
        return serviceDao.readAll(); // Metodo da implementare nel DAO
    }

    public static List<DailyTimeInterval> getAvailableTimeIntervals() { // Metodo per ottenere gli intervalli disponibili
        GenericDao<DailyTimeInterval> timeIntervalDao = getTimeIntervalDao();
        return timeIntervalDao.readAll();
    }

    public static GenericDao<Reservation> getReservationDao() {
        if (DATABASE.equalsIgnoreCase(storageOption)) {
            DaoFactory daoFactory = new DaoFactory();
            Connection connection = DatabaseConnectionManager.getConnection();
            return new ReservationDaoDB(connection,daoFactory);
        } else {
            if (reservationDaoMemoryInstance == null) {
                reservationDaoMemoryInstance = new ReservationDaoMemory();
            }
            return reservationDaoMemoryInstance;
        }
    }

    public static void setStorageOption(String option) {
        if (option != null && !option.isEmpty()) {
            storageOption = option;
        }
    }
}
