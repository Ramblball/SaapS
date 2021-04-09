package servers.handlers;

import com.sun.net.httpserver.HttpExchange;
import org.bson.Document;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;

public class ExchangeWrap {
    private final HttpExchange exchange;
    private final BufferedReader reader;
    private final OutputStream writer;

    public ExchangeWrap(HttpExchange exchange) {
        this.exchange = exchange;
        reader = new BufferedReader(new InputStreamReader(exchange.getRequestBody()));
        writer = exchange.getResponseBody();
    }

    public Document getRequest() throws IOException {
        StringBuilder builder = new StringBuilder();
        while (reader.ready()) {
            builder.append(reader.readLine());
        }
        return Document.parse(builder.toString());
    }

    public void sendResponse(String response, int code) throws IOException {
        exchange.getResponseHeaders().add("Content-Type", "application/json");
        exchange.sendResponseHeaders(code, response.length());
        writer.write(response.getBytes());
        writer.flush();
    }

    public void close() throws IOException {
        reader.close();
        writer.close();
        exchange.close();
    }
}
