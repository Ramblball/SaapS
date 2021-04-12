package servers.chat;

import database.models.Message;
import database.services.MessageService;
import org.bson.Document;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.*;

public class Chat {
    private static final MessageService messages = new MessageService();

    private static final Map<String, Connection> connections =
            Collections.synchronizedMap(new HashMap<>());
    private static ServerSocket server;

    public static void main(String[] args) {
        try {
            server = new ServerSocket(Integer.parseInt(System.getenv("CHAT_PORT")));

            while (true) {
                Socket socket = server.accept();

                new Connection(socket);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            closeAll();
        }
    }

    private static void closeAll() {
        try {
            server.close();

            synchronized (connections) {
                for (Connection connection : connections.values()) {
                    connection.close();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static class Connection extends Thread {
        private BufferedReader in;
        private PrintWriter out;
        private final Socket socket;
        private String userID;
        private String userName;

        public Connection(Socket socket) {
            this.socket = socket;

            try {
                in = new BufferedReader(new InputStreamReader(
                        socket.getInputStream()));
                out = new PrintWriter(socket.getOutputStream(), true);
                this.start();
            } catch (IOException e) {
                e.printStackTrace();
                close();
            }
        }

        @Override
        public void run() {
            try {
                while (true) {
                    String[] data = in.readLine().split("#:#");
                    switch (data[0]) {
                        case "init":
                            this.userName = data[1];
                            this.userID = data[0];
                            connections.put(userID, this);
                            break;
                        case "msg":
                            synchronized (connections) {
                                Message message = new Message(userID, data[1], data[2]);
                                messages.create(message);
                                if (connections.containsKey(data[1])) {
                                    Connection messageReceiver = connections.get(data[1]);
                                    messageReceiver.out.println(data[2]);
                                }
                            }
                            break;
                        case "exit":
                            return;
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                close();
            }
        }

        public void close() {
            try {
                in.close();
                out.close();
                socket.close();

                connections.remove(userID);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
