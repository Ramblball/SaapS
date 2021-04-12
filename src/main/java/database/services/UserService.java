package database.services;

import database.Literals;
import database.dao.DAOImpl;
import database.dao.UserDAO;
import database.models.User;
import org.bson.Document;
import org.bson.types.ObjectId;

/**
 * Класс - слой для взаимодействия приложения с базой данных пользователей
 */
public class UserService implements Service<User>{

    private static final DAOImpl userDao = new UserDAO();

    public User findById(ObjectId id) {
        return User.fromDocument(userDao.findById(id));
    }

    public User create(User entity) {
        entity.setId(userDao.create(entity));
        return entity;
    }

    public void update(User entity) {
        userDao.update(entity);
    }

    public void remove(User entity) {
        userDao.remove(entity.getId());
    }

    /**
     * Метод для поиска пользователя по имени
     * @param name      Имя пользователя
     * @return          Найденный пользователь
     */
    public User findByName(String name) {
        Document filter = new Document(Literals.FIELD_NAME, name);
        Document document = userDao.find(filter).first();
        assert document != null;
        return User.fromDocument(document);
    }
}
