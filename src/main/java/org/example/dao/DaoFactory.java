package org.example.dao;

import org.example.entity.Activity;
import org.example.entity.Service;
import org.example.entity.Client;
import org.example.entity.Receptionist;
import org.example.entity.StartupSettingsEntity;

import java.sql.Connection;
import java.util.List;

public class DaoFactory {
    private static ClientDaoMemory clientDaoMemoryInstance;
    private static ReceptionistDaoMemory receptionistDaoMemoryInstance;
    private static GenericDao<Activity> activityDaoMemoryInstance;
    private static GenericDao<Service> serviceDaoMemoryInstance;

    private DaoFactory() {
        // Costruttore privato per nascondere quello pubblico implicito
    }

    public static GenericDao<Client> getClientDao() {
        String storageOption = StartupSettingsEntity.getInstance().getStorageOption();
        if ("database".equalsIgnoreCase(storageOption)) {
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
        if ("database".equalsIgnoreCase(storageOption)) {
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
        if ("database".equalsIgnoreCase(storageOption)) {
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
        if ("database".equalsIgnoreCase(storageOption)) {
            Connection connection = DatabaseConnectionManager.getConnection();
            return new ServiceDaoDB(connection);
        } else {
            if (serviceDaoMemoryInstance == null) {
                serviceDaoMemoryInstance = new ServiceDaoMemory();
            }
            return serviceDaoMemoryInstance;
        }
    }

    // Nuovi metodi per ottenere liste di attività e servizi
    public static List<Activity> getAvailableActivities() {
        return getActivityDao().readAll(); // Metodo da implementare nel DAO
    }

    public static List<Service> getAvailableServices() {
        return getServiceDao().readAll(); // Metodo da implementare nel DAO
    }
}
