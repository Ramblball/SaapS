package database.exception;

/**
 * Класс ошибки, выбрасываемой, если запрос на поиск вернул null
 */
public class NotFoundException extends Exception{
    public NotFoundException(String message) {
        super(message);
    }
}
