package org.example.dao;

import org.example.entity.*;

import java.sql.Connection;
import java.util.List;

public class DaoFactory {
    private static ClientDaoMemory clientDaoMemoryInstance;
    private static ReceptionistDaoMemory receptionistDaoMemoryInstance;
    private static GenericDao<Activity> activityDaoMemoryInstance;
    private static GenericDao<Service> serviceDaoMemoryInstance;
    private static GenericDao<DailyTimeInterval> timeIntervalDaoMemoryInstance; // Aggiunto per TimeInterval
    private static GenericDao<Room> roomDaoMemoryInstance;

    private static final String DATABASE = "database";

    private DaoFactory() {
        // Costruttore privato per nascondere quello pubblico implicito
    }

    public static GenericDao<Client> getClientDao() {
        String storageOption = StartupSettingsEntity.getInstance().getStorageOption();
        if (DATABASE.equalsIgnoreCase(storageOption)) {
            Connection connection = DatabaseConnectionManager.getConnection();
            return new ClientDaoDB(connection);
        } else {
            if (clientDaoMemoryInstance == null) {
                clientDaoMemoryInstance = new ClientDaoMemory();
            }
            return clientDaoMemoryInstance;
        }
    }

    public static GenericDao<Receptionist> getReceptionistDao() {
        String storageOption = StartupSettingsEntity.getInstance().getStorageOption();
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

    public static GenericDao<Activity> getActivityDao() {
        String storageOption = StartupSettingsEntity.getInstance().getStorageOption();
        if (DATABASE.equalsIgnoreCase(storageOption)) {
            Connection connection = DatabaseConnectionManager.getConnection();
            return new ActivityDaoDB(connection);
        } else {
            if (activityDaoMemoryInstance == null) {
                activityDaoMemoryInstance = new ActivityDaoMemory();
            }
            return activityDaoMemoryInstance;
        }
    }

    public static GenericDao<Service> getServiceDao() {
        String storageOption = StartupSettingsEntity.getInstance().getStorageOption();
        if (DATABASE.equalsIgnoreCase(storageOption)) {
            Connection connection = DatabaseConnectionManager.getConnection();
            return new ServiceDaoDB(connection);
        } else {
            if (serviceDaoMemoryInstance == null) {
                serviceDaoMemoryInstance = new ServiceDaoMemory();
            }
            return serviceDaoMemoryInstance;
        }
    }

    public static GenericDao<DailyTimeInterval> getTimeIntervalDao() { // Metodo per TimeInterval
        String storageOption = StartupSettingsEntity.getInstance().getStorageOption();
        if (DATABASE.equalsIgnoreCase(storageOption)) {
            Connection connection = DatabaseConnectionManager.getConnection();
            return new TimeIntervalDaoDB(connection); // Implementazione per DB
        } else {
            if (timeIntervalDaoMemoryInstance == null) {
                timeIntervalDaoMemoryInstance = new TimeIntervalDaoMemory(); // Implementazione per memoria
            }
            return timeIntervalDaoMemoryInstance;
        }
    }

    public static GenericDao<Room> getRoomDao() {
        String storageOption = StartupSettingsEntity.getInstance().getStorageOption();
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
        GenericDao<Activity> activityDao = getActivityDao();
        return activityDao.readAll(); // Metodo da implementare nel DAO
    }

    public static List<Service> getAvailableServices() {
        GenericDao<Service> serviceDao = getServiceDao();
        return serviceDao.readAll(); // Metodo da implementare nel DAO
    }

    public static List<DailyTimeInterval> getAvailableTimeIntervals() { // Metodo per ottenere gli intervalli disponibili
        GenericDao<DailyTimeInterval> timeIntervalDao = getTimeIntervalDao();
        return timeIntervalDao.readAll();
    }
}
