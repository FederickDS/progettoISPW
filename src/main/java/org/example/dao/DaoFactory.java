package org.example.dao;

import org.example.entity.Client;
import org.example.entity.Receptionist;
import org.example.entity.StartupSettingsEntity;
import java.sql.Connection;

public class DaoFactory {
    private static ClientDaoMemory clientDaoMemoryInstance;
    private static ReceptionistDaoMemory receptionistDaoMemoryInstance;

    private DaoFactory(){
        //mettere costruttore privato, nascondere quello pubblico implicito
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
}
