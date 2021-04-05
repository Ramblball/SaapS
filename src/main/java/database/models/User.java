package database.models;

import org.bson.Document;
import org.bson.types.ObjectId;

import java.util.Map;

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
                    "name", userName,
                    "password", password,
                    "age", age));
        }
        return new Document(Map.of(
                "_id", id,
                "name", userName,
                "password", password,
                "age", age));
    }

    public static User fromDocument(Document document) {
        String userName = (String) document.get("name");
        String password = (String) document.get("password");
        Integer age = (Integer) document.get("age");
        User user = new User(userName, password, age);
        user.setId((String) document.get("_id"));
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
