package servers.handlers;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import database.models.User;
import database.services.UserService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.bson.Document;
import servers.Literals;

import java.io.IOException;

/**
 * Класс обработчик запроса на авторизацию пользователя
 */
@Handler(path = Literals.AUTHORIZE)
public class AuthHandler implements HttpHandler {
    private static final Logger logger = LogManager.getLogger(AuthHandler.class);

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
            User user = service.findByName(request.getString(Literals.KEY_NAME));
            if (user.checkPassword(request.getString(Literals.KEY_PASSWORD))) {
                Document clearUser = user.toDocument();
                clearUser.remove(Literals.KEY_PASSWORD);
                String response = clearUser.toJson();
                wExchange.sendResponse(response, 200);
            } else {
                wExchange.sendResponse(Literals.WRONG_PASS, 400);
            }
        }
        wExchange.close();
    }
}
