package database.services;

import database.Literals;
import database.dao.MessageDAO;
import database.models.Message;
import org.bson.Document;
import org.bson.types.ObjectId;

import java.util.ArrayList;
import java.util.List;

/**
 * Класс - слой для взаимодействия приложения с базой данных сообщений
 */
public class MessageService implements Service<Message> {

    private static final MessageDAO messageDao = new MessageDAO();

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

    /**
     * Метод для получения последних 20 сообщений,
     * отсортированных по времени отправки
     * @param firstId       _id первого пользователя
     * @param secondId      _id второго пользователя
     * @return              Переписку пользователей
     */
    public List<Message> getUsersMessages(ObjectId firstId, ObjectId secondId) {
        Document orderBy = new Document(Literals.FIELD_TIMESTAMP, -1);
        List<Message> list = new ArrayList<>();
        messageDao.findUsersMessages(firstId, secondId)
                .sort(orderBy)
                .limit(20)
                .forEach((document) -> {
            list.add(new Message(document));
        });
        return list;
    }
}
