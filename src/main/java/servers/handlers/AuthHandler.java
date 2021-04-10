package servers.handlers;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import database.models.User;
import database.services.UserService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.bson.Document;
import org.bson.types.ObjectId;

import java.io.IOException;

/**
 * Класс обработчик запроса на авторизацию пользователя
 */
@Handler(path = "/authorize")
public class AuthHandler implements HttpHandler {
    private static final Logger logger = LogManager.getLogger(AuthHandler.class);

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        ExchangeWrapper wExchange = new ExchangeWrapper(exchange);
        UserService service = new UserService();
        wExchange.closeIfNotAllowed("GET");

        if (exchange.getRequestMethod().equals("GET")) {
            Document request = wExchange.getRequest();
            logger.debug(exchange.getRequestURI().getPath() +
                    " ( " + exchange.getRequestMethod() + " ) -> " +
                    request.toJson());
            User user = service.findByName(request.getString("name"));
            if (user.checkPassword(request.getString("password"))) {
                String response = user.toDocument().toJson();
                wExchange.sendResponse(response, 200);
            } else {
                String response = "No no no";
                wExchange.sendResponse(response, 200);
            }
        }
        wExchange.close();
    }
}
