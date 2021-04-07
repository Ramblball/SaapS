package database.services;

import org.bson.types.ObjectId;

public interface Service<T> {
    /**
     * Метод для поиска объекта по id
     * Переводит найденный Document в объект
     * @param id        _id объекта
     * @return          Найденный объект
     */
    T findById(ObjectId id);

    /**
     * Метод для сохранения объекта в базу данных
     * Устанавливает выданный _id
     * @param entity      Объект для сохранения
     * @return          Объект в выданны _id
     */
    T create(T entity);

    /**
     * Метод для обновления объекта в базе данных
     * @param entity      Объект для обновления
     */
    void update(T entity);

    /**
     * Метод для удаления объекта из базы данных
     * @param entity      Объект
     */
    void remove(T entity);
}
