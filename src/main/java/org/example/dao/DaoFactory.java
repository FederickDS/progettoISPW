package org.example.dao;

import org.example.entity.*;

import java.awt.image.DataBufferShort;
import java.sql.Connection;
import java.util.List;

public class DaoFactory implements DaoFactoryInterface{
    private static final String DATABASE = "database";
    private static final String FILE = "file";
    private static final String STATELESS = "stateless";

    private static String storageOption = STATELESS;
    private static ClientDaoMemory clientDaoMemoryInstance;
    private static GenericDao<Client> clientDaoFileInstance;
    private static ReceptionistDaoMemory receptionistDaoMemoryInstance;
    private static ReceptionistDaoFile receptionistDaoFileInstance;
    private static GenericDao<Activity> activityDaoMemoryInstance;
    private static GenericDao<Activity> activityDaoFileInstance;
    private static GenericDao<Service> serviceDaoMemoryInstance;
    private static GenericDao<Service> serviceDaoFileInstance;
    private static GenericDao<DailyTimeInterval> timeIntervalDaoMemoryInstance; // Aggiunto per TimeInterval
    private static DailyTimeIntervalDaoFile timeIntervalDaoFileInstance;
    private static GenericDao<Room> roomDaoMemoryInstance;
    private static RoomDaoFile roomDaoFileInstance;
    private static ReservationDaoFile reservationDaoFileInstance;
    private static GenericDao<Reservation> reservationDaoMemoryInstance;

    public DaoFactory() {
        // Costruttore privato per nascondere quello pubblico implicito
    }

    public GenericDao<Client> getClientDao() {
        if (DATABASE.equalsIgnoreCase(storageOption)) {
            Connection connection = DatabaseConnectionManager.getConnection();
            return new ClientDaoDB(connection);
        } else if (FILE.equalsIgnoreCase(storageOption)) {
            return getClientFileInstance();
        } else {
            return getClientMemoryInstance();
        }
    }

    public static GenericDao<Client> getClientFileInstance() {
        if (clientDaoFileInstance == null) {
            clientDaoFileInstance = new ClientDaoFile();
        }
        return clientDaoFileInstance;
    }

    public static ClientDaoMemory getClientMemoryInstance(){
        if (clientDaoMemoryInstance == null) {
            clientDaoMemoryInstance = new ClientDaoMemory();
        }
        return clientDaoMemoryInstance;
    }

    public static GenericDao<Receptionist> getReceptionistDao() {
        if (DATABASE.equalsIgnoreCase(storageOption)) {
            Connection connection = DatabaseConnectionManager.getConnection();
            return new ReceptionistDaoDB(connection);
        } else if (FILE.equalsIgnoreCase(storageOption)) {
            return getReceptionistFileInstance();
        } else {
            if (receptionistDaoMemoryInstance == null) {
                receptionistDaoMemoryInstance = new ReceptionistDaoMemory();
            }
            return receptionistDaoMemoryInstance;
        }
    }

    public static ReceptionistDaoFile getReceptionistFileInstance() {
        if (receptionistDaoFileInstance == null) {
            receptionistDaoFileInstance = new ReceptionistDaoFile();
        }
        return receptionistDaoFileInstance;
    }

    public GenericDao<Activity> getActivityDao() {
        if (DATABASE.equalsIgnoreCase(storageOption)) {
            Connection connection = DatabaseConnectionManager.getConnection();
            return new ActivityDaoDB(connection);
        } else if (FILE.equalsIgnoreCase(storageOption)) {
            return getActivityFileInstance();
        } else {
            return getActivityMemoryInstance();
        }
    }

    public static GenericDao<Activity> getActivityFileInstance() {
        if (activityDaoFileInstance == null) {
            activityDaoFileInstance = new ActivityDaoFile();
        }
        return activityDaoFileInstance;
    }

    public static ActivityDaoMemory getActivityMemoryInstance(){
        if (activityDaoMemoryInstance == null) {
            activityDaoMemoryInstance = new ActivityDaoMemory();
        }
        return (ActivityDaoMemory) activityDaoMemoryInstance;
    }

    public GenericDao<Service> getServiceDao() {
        if (DATABASE.equalsIgnoreCase(storageOption)) {
            Connection connection = DatabaseConnectionManager.getConnection();
            return new ServiceDaoDB(connection);
        } else if (FILE.equalsIgnoreCase(storageOption)) {
            return getServiceFileInstance();
        } else {
            return getServiceMemoryInstance();
        }
    }

    public static GenericDao<Service> getServiceFileInstance() {
        if (serviceDaoFileInstance == null) {
            serviceDaoFileInstance = new ServiceDaoFile();
        }
        return serviceDaoFileInstance;
    }

    public static ServiceDaoMemory getServiceMemoryInstance(){
        if (serviceDaoMemoryInstance == null) {
            serviceDaoMemoryInstance = new ServiceDaoMemory();
        }
        return (ServiceDaoMemory) serviceDaoMemoryInstance;
    }

    public static GenericDao<DailyTimeInterval> getTimeIntervalDao() {
        if (DATABASE.equalsIgnoreCase(storageOption)) {
            Connection connection = DatabaseConnectionManager.getConnection();
            return new DailyTimeIntervalDaoDB(connection);
        } else if (FILE.equalsIgnoreCase(storageOption)) { // CORRETTO!
            return getTimeIntervalFileInstance();
        } else {
            if (timeIntervalDaoMemoryInstance == null) {
                timeIntervalDaoMemoryInstance = new DailyTimeIntervalDaoMemory();
            }
            return timeIntervalDaoMemoryInstance;
        }
    }

    public static DailyTimeIntervalDaoFile getTimeIntervalFileInstance() {
        if (timeIntervalDaoFileInstance == null) {
            timeIntervalDaoFileInstance = new DailyTimeIntervalDaoFile();
        }
        return timeIntervalDaoFileInstance;
    }

    public static GenericDao<Room> getRoomDao() {
        if (DATABASE.equalsIgnoreCase(storageOption)) {
            Connection connection = DatabaseConnectionManager.getConnection();
            return new RoomDaoDB(connection);
        } else if (FILE.equalsIgnoreCase(storageOption)) {
            return getRoomFileInstance();
        } else {
            if (roomDaoMemoryInstance == null) {
                roomDaoMemoryInstance = new RoomDaoMemory();
            }
            return roomDaoMemoryInstance;
        }
    }

    public static RoomDaoFile getRoomFileInstance() {
        if (roomDaoFileInstance == null) {
            roomDaoFileInstance = new RoomDaoFile();
        }
        return roomDaoFileInstance;
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
            return new ReservationDaoDB(connection, daoFactory);
        } else if (FILE.equalsIgnoreCase(storageOption)) {
            return getReservationFileInstance();
        } else {
            if (reservationDaoMemoryInstance == null) {
                reservationDaoMemoryInstance = new ReservationDaoMemory();
            }
            return reservationDaoMemoryInstance;
        }
    }

    public static ReservationDaoFile getReservationFileInstance() {
        if (reservationDaoFileInstance == null) {
            reservationDaoFileInstance = new ReservationDaoFile();
        }
        return reservationDaoFileInstance;
    }

    public static void setStorageOption(String option) {
        if (option != null && !option.isEmpty()) {
            storageOption = option;
        }
    }
}
