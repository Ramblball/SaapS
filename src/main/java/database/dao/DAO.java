package database.dao;

import database.exception.NotFoundException;

public interface DAO<T> {
    T findById(String id) throws NotFoundException;

    void create(T entity);

    void update(T entity);

    void remove(T entity);
}
