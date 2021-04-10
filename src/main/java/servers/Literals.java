package servers;

public final class Literals {
    /* Chat constants */


    /* Server constants */

    // Методы запросов
    public static final String METHOD_GET = "GET";
    public static final String METHOD_POST = "POST";

    // Ключи для полей запроса
    public static final String KEY_ID = "_id";
    public static final String KEY_NAME = "name";
    public static final String KEY_PASSWORD = "password";

    // Сообщения при ошибках
    public static final String MESSAGE_405 = "Method not allowed";
    public static final String WRONG_PASS = "Invalid password";

    // Заголовки запроса
    public static final String CONTENT_TYPE = "Content-Type";
    public static final String APPLICATION_JSON = "application/json";

    // Пути обработчиков
    public static final String REGISTRATION = "/registration";
    public static final String AUTHORIZE = "/authorize";
}
