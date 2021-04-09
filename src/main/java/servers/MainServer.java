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
import java.util.Optional;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

public class MainServer {
    private static final Logger logger = LogManager.getLogger(MainServer.class);

    HttpServer server;

    public void start() {
        try {
            ThreadPoolExecutor poolExecutor = (ThreadPoolExecutor) Executors.newFixedThreadPool(20);
            server = HttpServer.create(new InetSocketAddress("127.0.0.1", 3000), 0);
            setHandlers();
            server.setExecutor(poolExecutor);
            server.start();
        } catch (IOException | HandlerAnnotationException ex) {
            logger.debug(ex.getMessage(), ex);
            if (server != null) {
                close();
            }
        }
    }

    public void close() {
        server.stop(0);
    }

    private void setHandlers() throws HandlerAnnotationException {
        Reflections reflections = new Reflections("servers.handlers");
        for (Class<?> clazz : reflections.getTypesAnnotatedWith(Handler.class)) {
            String path = clazz.getAnnotation(Handler.class).path();
            HttpHandler handler = toHandler(clazz);
            server.createContext(path, handler);
        }
    }

    private static HttpHandler toHandler(Class<?> clazz) throws HandlerAnnotationException {
        try {
            return Optional.of((HttpHandler) clazz
                    .getConstructor()
                    .newInstance()).get();
        } catch (ClassCastException |
                InstantiationException |
                IllegalAccessException |
                InvocationTargetException |
                NoSuchMethodException ex) {
            throw new HandlerAnnotationException(clazz.getName(), ex);
        }
    }
}
