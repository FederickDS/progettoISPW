package org.example.dao;

import org.example.entity.Activity;
import org.example.entity.Client;
import org.example.entity.Service;

public interface DaoFactoryInterface {
    GenericDao<Activity> getActivityDao();
    GenericDao<Client> getClientDao();
    GenericDao<Service> getServiceDao();
}
