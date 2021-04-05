import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import servers.chat.Chat;

public class Main {
    private static final Logger logger = LogManager.getLogger(Main.class);

    public static void main(String[] args) {
        new Chat().start();

        logger.debug("Server started");
    }
}
