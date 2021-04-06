package database.dao;

import database.models.Entity;
import org.bson.Document;
import org.bson.types.ObjectId;

/**
 * Интерфейс, описывающий CRUD операции для работы с базой данных
 */
public interface DAO {

    /**
     * Метод для поиска объекта по его _id в базе данных
     * @param id                _id объекта
     * @return                  Найденный объект
     */
    Document findById(ObjectId id);

    /**
     * Метод для сохранения объекта в базу данных
     * @param entity            Объект для сохранения
     */
    ObjectId create(Entity entity);

    /**
     * Метод для обновления объекта в базе данных
     * @param entity            Объект для обновления
     */
    void update(Entity entity);

    /**
     * Метод для удаления объекта из базы данных
     * @param id                _id объекта
     */
    void remove(ObjectId id);
}
