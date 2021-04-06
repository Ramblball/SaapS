package database.services;

import database.dao.UserDAO;
import database.models.User;
import org.bson.types.ObjectId;

/**
 * Класс - слой для взаимодействия приложения с базой данных пользователей
 */
public class UserService {

    private static final UserDAO userDao = new UserDAO();

    /**
     * Метод для поиска пользователя по id
     * Переводит найденный Document в объект пользователя
     * @param id        _id пользователя
     * @return          Объект пользователя
     */
    public User findById(ObjectId id) {
        return new User(userDao.findById(id));
    }

    /**
     * Метод для сохранения объекта пользователя в базу данных
     * Устанавливает выданный _id
     * @param user      Объект пользователя для сохранения
     * @return          Объект пользователя в выданны _id
     */
    public User create(User user) {
        user.setId(userDao.create(user));
        return user;
    }

    /**
     * Метод для обновления объекта пользователя в базе данных
     * @param user      Объект пользователя для обновления
     */
    public void update(User user) {
        userDao.update(user);
    }

    /**
     * Метод для удаления объекта пользователя из базы данных
     * @param user      Объект пользователя
     */
    public void remove(User user) {
        userDao.remove(user.getId());
    }
}
