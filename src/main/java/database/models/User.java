package database.models;

import database.Literals;
import org.bson.Document;
import org.bson.types.ObjectId;

import java.util.HashMap;
import java.util.Map;

/**
 * Класс - описание сущности пользователя
 */
public class User implements Entity{
    private ObjectId id;
    private String userName;
    private String password;
    private Double age;

    public Document toDocument() {
        Map<String, Object> map = new HashMap<>();
        map.put(Literals.FIELD_NAME, userName);
        map.put(Literals.FIELD_PASSWORD, password);
        map.put(Literals.FIELD_AGE, age);
        if (id != null) {
            map.put(Literals.FIELD_ID, id.toString());
        }
        return new Document(map);
    }

    public static User fromDocument(Document document) {
        User user =  new User(
                document.getString(Literals.FIELD_NAME),
                document.getString(Literals.FIELD_PASSWORD),
                document.getDouble(Literals.FIELD_AGE)
        );
        if (document.containsKey(Literals.FIELD_ID)) {
            user.setId(document.getObjectId(Literals.FIELD_ID));
        }
        return user;
    }

    private User(String name, String password, Double age) {
        this.userName = name;
        this.password = password;
        this.age = age;
    }

    public void setId(ObjectId  id) {
        this.id = id;
    }

    public ObjectId  getId() {
        return id;
    }

    public boolean checkPassword(String password) {
        return this.password.equals(password);
    }
}
