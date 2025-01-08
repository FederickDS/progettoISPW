package org.example.dao;

import java.sql.SQLException;

public interface GenericDao<T> {
    void create(T entity) throws SQLException;
    T read(String email) throws SQLException;
    void update(T entity) throws SQLException;
    void delete(String email) throws SQLException;
}
