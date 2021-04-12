package servers.handlers;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import database.models.User;
import database.services.UserService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.bson.Document;
import servers.Literals;

import java.io.*;

/**
 * Класс обработчик запроса на регистрацию пользователя
 */
@Handler(path = Literals.REGISTRATION)
public class RegistrationHandler implements HttpHandler {
    private static final Logger logger = LogManager.getLogger(RegistrationHandler.class);

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        ExchangeWrapper wExchange = new ExchangeWrapper(exchange);
        UserService service = new UserService();
        wExchange.closeIfNotAllowed(Literals.METHOD_POST);

        if (exchange.getRequestMethod().equals(Literals.METHOD_POST)) {
            Document request = wExchange.getRequest();
            logger.debug(exchange.getRequestURI().getPath() +
                    " ( " + exchange.getRequestMethod() + " ) -> " +
                    request.toJson());
            User user = service.create(User.fromDocument(request));
            String response = user.toDocument().toJson();
            wExchange.sendResponse(response, 200);
        }
        wExchange.close();
    }
}
