package database.dao;

import com.mongodb.client.MongoCollection;
import database.Literals;
import database.models.Entity;
import org.bson.BsonValue;
import org.bson.Document;
import org.bson.types.ObjectId;

import java.util.Map;

/**
 * Класс, реализующий запросы к базе данных
 */
public abstract class DAOImpl implements DAO {

    /**
     * Метод для получения коллекции, по ее названию
     * @return          Коллекция
     */
    protected abstract MongoCollection<Document> getCollection();

    public Document findById(ObjectId id){
        MongoCollection<Document> collection = getCollection();
        return collection.find(new Document(Literals.FIELD_ID, id)).first();
    }

    public ObjectId create(Entity entity) {
        MongoCollection<Document> collection = getCollection();
        BsonValue value = collection.insertOne(entity.toDocument()).getInsertedId();
        assert value != null;
        return value.asObjectId().getValue();
    }

    public void update(Entity entity) {
        MongoCollection<Document> collection = getCollection();
        collection.findOneAndUpdate(
                new Document(Map.of(Literals.FIELD_ID, entity.getId())),
                new Document(Map.of(Literals.MONGO_SET_STATEMENT, entity.toDocument())));
    }

    public void remove(ObjectId id) {
        MongoCollection<Document> collection = getCollection();
        collection.deleteOne(new Document(Map.of(Literals.FIELD_ID, id)));
    }
}
