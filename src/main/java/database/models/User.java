package database.models;

import database.Literals;
import org.bson.Document;

import java.util.Map;

/**
 * Класс - описание сущности пользователя
 */
public class User {
    private String id;
    private String userName;
    private String password;
    private Integer age;

    public User(String userName, String password, Integer age) {
        this.userName = userName;
        this.password = password;
        this.age = age;
    }

    public Document toDocument() {
        if (id == null) {
            return new Document(Map.of(
                    Literals.FIELD_NAME, userName,
                    Literals.FIELD_PASSWORD, password,
                    Literals.FIELD_AGE, age));
        }
        return new Document(Map.of(
                Literals.FIELD_ID, id,
                Literals.FIELD_NAME, userName,
                Literals.FIELD_PASSWORD, password,
                Literals.FIELD_AGE, age));
    }

    public static User fromDocument(Document document) {
        String userName = (String) document.get(Literals.FIELD_NAME);
        String password = (String) document.get(Literals.FIELD_PASSWORD);
        Integer age = (Integer) document.get(Literals.FIELD_AGE);
        User user = new User(userName, password, age);
        user.setId((String) document.get(Literals.FIELD_ID));
        return user;
    }

    private void setId(String  id) {
        this.id = id;
    }

    public String  getId() {
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

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }
}
