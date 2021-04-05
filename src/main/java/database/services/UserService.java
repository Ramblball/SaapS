package database.services;

import database.dao.UserDAOImpl;
import database.exception.NotFoundException;
import database.models.User;

public class UserService {
    private static final UserDAOImpl userDAO = new UserDAOImpl();

    public User findById(String id) {
        try {
            return userDAO.findById(id);
        } catch (NotFoundException ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public void save(User user) {
        userDAO.create(user);
    }

    public void update(User user) {
        userDAO.update(user);
    }
}
