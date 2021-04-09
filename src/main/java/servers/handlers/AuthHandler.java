package servers.handlers;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import database.models.User;
import database.services.UserService;
import org.bson.types.ObjectId;

import java.io.IOException;

@Handler(path = "/authorize")
public class AuthHandler implements HttpHandler {

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        UserService service = new UserService();
        ExchangeWrap wExchange = new ExchangeWrap(exchange);
        User user = service.findById(new ObjectId(wExchange.getRequest().getString("_id")));
        String response = user.toDocument().toJson();
        wExchange.sendResponse(response, 200);
        wExchange.close();
    }
}
