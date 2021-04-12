import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import servers.MainServer;
import servers.chat.Chat;

public class Main {
    private static final Logger logger = LogManager.getLogger(Main.class);

    public static void main(String[] args) {
        MainServer.main(new String[0]);
        logger.debug("Main server started");
        Chat.main(new String[0]);
        logger.debug("Chat server started");
    }
}
