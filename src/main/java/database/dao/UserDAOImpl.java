package database.dao;

import com.mongodb.client.MongoCollection;
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
        return MongoClientFactory.getClientFactory().getDatabase("main").getCollection(name);
    }

    public User findById(String id) throws NotFoundException{
        MongoCollection<Document> collection = getCollection("user");
        Document user = collection.find(new Document("_id", id)).first();
        if (user == null) {
            throw new NotFoundException("User: " + id + "not found");
        }
        return User.fromDocument(user);
    }

    public void create(User user) {
        MongoCollection<Document> collection = getCollection("user");
        collection.insertOne(user.toDocument());
    }

    public void update(User user) {
        MongoCollection<Document> collection = getCollection("user");
        collection.findOneAndUpdate(new Document("_id", user.getId()), user.toDocument());
    }

    public void remove(User user) {
        MongoCollection<Document> collection = getCollection("user");
        collection.deleteOne(new Document("_id", user.getId()));
    }
}
