package servers.handlers;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import database.models.User;
import database.services.UserService;

import java.io.*;

@Handler(path = "/registration")
public class RegistrationHandler implements HttpHandler {

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        if (exchange.getRequestMethod().equals("POST")) {
            UserService service = new UserService();
            ExchangeWrap wExchange = new ExchangeWrap(exchange);
            User user = service.create(User.fromDocument(wExchange.getRequest()));
            String response = user.toDocument().toJson();
            wExchange.sendResponse(response, 200);
            wExchange.close();
        }
    }
}
