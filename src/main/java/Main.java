import database.utils.MongoClientFactory;
import servers.chat.Chat;

public class Main {
    public static void main(String[] args) {
        new Chat().start();
	MongoClientFactory.getClientFactory();
    }
}
