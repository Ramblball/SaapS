package database.services;

import database.dao.DAOImpl;
import database.dao.MessageDAO;
import database.models.Message;
import org.bson.types.ObjectId;

/**
 * Класс - слой для взаимодействия приложения с базой данных сообщений
 */
public class MessageService implements Service<Message> {

    private static final DAOImpl messageDao = new MessageDAO();

    public Message findById(ObjectId id) {
        return new Message(messageDao.findById(id));
    }

    public Message create(Message entity) {
        entity.setId(messageDao.create(entity));
        return entity;
    }

    public void update(Message entity) {
        messageDao.update(entity);
    }

    public void remove(Message entity) {
        messageDao.remove(entity.getId());
    }
}
