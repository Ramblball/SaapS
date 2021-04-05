package database.dao;

import database.exception.NotFoundException;

/**
 * Интерфейс, описывающий CRUD операции для работы с базой данных
 * @param <T>   Класс сущности
 */
public interface DAO<T> {

    /**
     * Метод для поиска объекта по его _id в базе данных
     * @param id                    _id объекта
     * @return                      Найденный объект
     * @throws NotFoundException    Объект не найден
     */
    T findById(String id) throws NotFoundException;

    /**
     * Метод для сохранения объекта в базу данных
     * @param entity                Объект для сохранения
     */
    void create(T entity);

    /**
     * Метод для обновления объекта в базе данных
     * @param entity                Объект для обновления
     */
    void update(T entity);

    /**
     * Метод для удаления объекта из базы данных
     * @param entity                Объект для удаления
     */
    void remove(T entity);
}
