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
            map.put(Literals.FIELD_ID, id);
        }
        return new Document(map);
    }

    public User(Document document) {
        this.userName = document.getString(Literals.FIELD_NAME);
        this.password = document.getString(Literals.FIELD_PASSWORD);
        this.age = document.getDouble(Literals.FIELD_AGE);
        if (document.containsKey(Literals.FIELD_ID)) {
            this.id = document.getObjectId(Literals.FIELD_ID);
        }
    }

    public void setId(ObjectId  id) {
        this.id = id;
    }

    public ObjectId  getId() {
        return id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Double getAge() {
        return age;
    }

    public void setAge(Double age) {
        this.age = age;
    }
}
