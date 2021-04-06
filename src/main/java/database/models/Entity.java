package database.models;

import org.bson.Document;
import org.bson.types.ObjectId;

/**
 * Интерфейс сущности, хранимой в базе данных
 */
public interface Entity {

    /**
     * Метод для перевода объекта сущности в Document
     * @return      Document представление сущности
     */
    Document toDocument();

    /**
     * Метод для получения _id сущности в базе данных
     * @return      _id сущности
     */
    ObjectId getId();
}
