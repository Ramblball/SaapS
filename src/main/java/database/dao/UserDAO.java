package database.dao;

import com.mongodb.client.MongoCollection;
import database.Literals;
import database.utils.MongoClientFactory;
import org.bson.Document;

/**
 * Класс для взаимодействия с коллекцией пользователей
 */
public class UserDAO extends DAOImpl {

    @Override
    protected MongoCollection<Document> getCollection() {
        return MongoClientFactory
                .getClientFactory()
                .getDatabase(Literals.DATABASE_MAIN)
                .getCollection(Literals.COLLECTION_USER);
    }
}
