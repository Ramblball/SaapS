import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import servers.MainServer;
import servers.chat.Chat;

public class Main {
    private static final Logger logger = LogManager.getLogger(Main.class);

    public static void main(String[] args) {
        MainServer mainServer = new MainServer();
        Chat chat = new Chat();
        mainServer.start();
        logger.debug("Main server started");
        chat.start();
        logger.debug("Chat server started");
    }
}
