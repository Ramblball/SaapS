package database.dao;

import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import database.Literals;
import database.utils.MongoClientFactory;
import org.bson.Document;
import org.bson.types.ObjectId;

import java.util.Map;

/**
 * Класс для взаимодействия с коллекцией пользователей
 */
public class MessageDAO extends DAOImpl{
    @Override
    protected MongoCollection<Document> getCollection() {
        return MongoClientFactory
                .getClientFactory()
                .getDatabase(Literals.DATABASE_MAIN)
                .getCollection(Literals.COLLECTION_MESSAGE);
    }

    /**
     * Метод для получения переписки
     * @param firstId        _id первого пользователя
     * @param secondId      _id второго пользователя
     * @return              Переписку пользователей
     */
    public FindIterable<Document> findUsersMessages(ObjectId firstId, ObjectId secondId) {
        Document[] fields = new Document[] {
                new Document(Map.of(Literals.FIELD_SENDER, firstId, Literals.FIELD_RECEIVER, secondId)),
                new Document(Map.of(Literals.FIELD_SENDER, secondId, Literals.FIELD_RECEIVER, firstId)),
        };
        Document query = new Document("$end", fields);
        MongoCollection<Document> messages = getCollection();
        return messages.find(query);
    }
}
