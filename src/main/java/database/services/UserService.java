package database.services;

import database.dao.DAOImpl;
import database.dao.UserDAO;
import database.models.User;
import org.bson.types.ObjectId;

/**
 * Класс - слой для взаимодействия приложения с базой данных пользователей
 */
public class UserService implements Service<User>{

    private static final DAOImpl userDao = new UserDAO();

    public User findById(ObjectId id) {
        return new User(userDao.findById(id));
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
}
