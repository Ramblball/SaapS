package servers;

import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.reflections.Reflections;
import servers.exceptions.HandlerAnnotationException;
import servers.handlers.Handler;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.net.InetSocketAddress;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * Класс запуска основного HTTP сервера приложения
 */
public class MainServer {
    private static final Logger logger = LogManager.getLogger(MainServer.class);

    private static HttpServer server;

    /**
     * Метод для запуска сервера
     */
    public static void main(String[] args) {
        try {
            ThreadPoolExecutor poolExecutor = (ThreadPoolExecutor) Executors.newFixedThreadPool(20);
            server = HttpServer.create(new InetSocketAddress(Integer.parseInt(System.getenv("PORT"))), 0);
            setHandlers();
            server.setExecutor(poolExecutor);
            server.start();
            logger.debug("Main server started");
        } catch (IOException | HandlerAnnotationException ex) {
            logger.debug(ex.getMessage(), ex);
            if (server != null) {
                close();
            }
        }
    }

    /**
     * Метод для остановки сервера
     */
    public static void close() {
        server.stop(0);
    }

    /**
     * Метод добавления обработчиков запросов к серверу
     * Ищет классы обработчики по анотации
     * @throws HandlerAnnotationException       Ошибка при приведении аннотированного класса к обработчику
     */
    private static void setHandlers() throws HandlerAnnotationException {
        Reflections reflections = new Reflections("servers.handlers");
        for (Class<?> clazz : reflections.getTypesAnnotatedWith(Handler.class)) {
            String path = clazz.getAnnotation(Handler.class).path();
            HttpHandler handler = toHandler(clazz);
            server.createContext(path, handler);
        }
    }

    /**
     * Метод для создания экземпляра обработчика из аннотированого класса
     * @param clazz                             Аннотированный класс
     * @return                                  Экземпляр обработчика
     * @throws HandlerAnnotationException       Ошибка при приведении аннотированного класса к обработчику
     */
    private static HttpHandler toHandler(Class<?> clazz) throws HandlerAnnotationException {
        try {
            return (HttpHandler) clazz
                    .getConstructor()
                    .newInstance();
        } catch (ClassCastException |
                InstantiationException |
                IllegalAccessException |
                InvocationTargetException |
                NoSuchMethodException ex) {
            throw new HandlerAnnotationException(clazz.getName(), ex);
        }
    }
}
