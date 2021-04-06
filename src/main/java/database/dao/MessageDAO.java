package database.dao;

import com.mongodb.client.MongoCollection;
import database.Literals;
import database.utils.MongoClientFactory;
import org.bson.Document;

public class MessageDAO extends DAOImpl{
    @Override
    protected MongoCollection<Document> getCollection() {
        return MongoClientFactory
                .getClientFactory()
                .getDatabase(Literals.DATABASE_MAIN)
                .getCollection(Literals.COLLECTION_MESSAGE);
    }
}
