package database.dao;

import com.mongodb.client.MongoCollection;
import database.Literals;
import database.exception.NotFoundException;
import database.models.User;
import database.utils.MongoClientFactory;
import org.bson.Document;

/**
 * Класс, реализующий запросы к базе данных
 */
public class UserDAOImpl implements DAO<User> {

    /**
     * Метод для получения коллекции, по ее названию
     * @param name      Название коллекции
     * @return          Коллекция
     */
    private MongoCollection<Document> getCollection(String name) {
        return MongoClientFactory.getClientFactory().getDatabase(Literals.DATABASE_MAIN).getCollection(name);
    }

    public User findById(String id) throws NotFoundException{
        MongoCollection<Document> collection = getCollection(Literals.COLLECTION_USER);
        Document user = collection.find(new Document(Literals.FIELD_ID, id)).first();
        if (user == null) {
            throw new NotFoundException(Literals.EXCEPTION_NOT_FOUND_MESSAGE(id));
        }
        return User.fromDocument(user);
    }

    public void create(User user) {
        MongoCollection<Document> collection = getCollection(Literals.COLLECTION_USER);
        collection.insertOne(user.toDocument());
    }

    public void update(User user) {
        MongoCollection<Document> collection = getCollection(Literals.COLLECTION_USER);
        collection.findOneAndUpdate(new Document(Literals.FIELD_ID, user.getId()), user.toDocument());
    }

    public void remove(User user) {
        MongoCollection<Document> collection = getCollection(Literals.COLLECTION_USER);
        collection.deleteOne(new Document(Literals.FIELD_ID, user.getId()));
    }
}
