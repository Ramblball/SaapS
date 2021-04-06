package database;

/**
 * Класс с текстовыми литералами
 */
public final class Literals {
    /* Константы для dao package */

    // Название базы данных основново приложения
    public static final String DATABASE_MAIN = "main";
    // Название коллекции с объектами пользоваиелей
    public static final String COLLECTION_USER = "user";

    /* Константы команд MongoDB */

    public static final String MONGO_SET_STATEMENT = "$set";

    /* Константы для моделей */

    // Название поля с id объекта
    public static final String FIELD_ID = "_id";
    // Название поля с паролем пользователя
    public static final String FIELD_PASSWORD = "password";
    // Название поля с именем пользоваеля
    public static final String FIELD_NAME = "name";
    // Название поля с возрастом пользователя
    public static final String FIELD_AGE = "age";
}
