package servers.handlers;

import com.sun.net.httpserver.HttpExchange;
import org.bson.Document;
import org.apache.logging.log4j.Logger;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.Arrays;

/**
 * Класс - обертка для объекта запроса
 */
public class ExchangeWrap {
    private final HttpExchange exchange;
    private final BufferedReader reader;
    private final OutputStream writer;

    public ExchangeWrap(HttpExchange exchange) {
        this.exchange = exchange;
        reader = new BufferedReader(new InputStreamReader(exchange.getRequestBody()));
        writer = exchange.getResponseBody();
    }

    /**
     * Закрывает соединение, если метод не поддерживается
     * @param allowedMethods    Список, поддерживаемых методов
     * @throws IOException      Ошибка при закрытии потоков
     */
    public void closeIfNotAllowed(String... allowedMethods) throws IOException {
        if (Arrays.stream(allowedMethods).noneMatch(
                (method) -> exchange.getRequestMethod().equals(method)
        )) {
            this.sendResponse("Method not allowed", 405);
            close();
        }
    }

    /**
     * Метод для получения данных из тела запроса
     * @return                  Тело запроса, преобразованное в Document
     * @throws IOException      Ошибка при работе в потоком ввода
     */
    public Document getRequest() throws IOException {
        StringBuilder builder = new StringBuilder();
        while (reader.ready()) {
            builder.append(reader.readLine());
        }
        return Document.parse(builder.toString());
    }

    /**
     * Метод для отправки данных в ответ на запрос
     * @param response          Передаваемое сообщение
     * @param code              Статус код ответа
     * @throws IOException      Ошибка при работе с потоком вывода
     */
    public void sendResponse(String response, int code) throws IOException {
        exchange.getResponseHeaders().add("Content-Type", "application/json");
        exchange.sendResponseHeaders(code, response.length());
        writer.write(response.getBytes());
        writer.flush();
    }

    /**
     * Метод для закрытия потоков и завершения жизни объекта запроса
     * @throws IOException      Ошибка при закрытии потоков
     */
    public void close() throws IOException {
        reader.close();
        writer.close();
        exchange.close();
    }
}
