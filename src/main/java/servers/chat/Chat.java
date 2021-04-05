package servers.chat;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.*;

public class Chat extends Thread{
    private final Map<Integer, Connection> connections =
        Collections.synchronizedMap(new HashMap<>());
    private ServerSocket server;

    @Override
    public void run() {
        try {
            server = new ServerSocket(8080);

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

    private void closeAll() {
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

    private class Connection extends Thread {
        private BufferedReader in;
        private PrintWriter out;
        private final Socket socket;
        private Integer userID;
        private String userName;

        //TODO: Подгружать последние сообщения из бд и отправлять юзеру
        public Connection(Socket socket) {
            this.socket = socket;

            try {
                in = new BufferedReader(new InputStreamReader(
                        socket.getInputStream()));
                out = new PrintWriter(socket.getOutputStream(), true);
                String[] initData = this.in.readLine().split("#:#");
                this.userName = initData[1];
                this.userID = Integer.parseInt(initData[0]);
                connections.put(userID, this);
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
                        case "exit":
                            return;
                        case "all":
                            synchronized (connections) {
                                for (Connection connection : connections.values()) {
                                    connection.out.println(userName + ": " + data[1]);
                                }
                            }
                            break;
                        case "msg":
                            //TODO: Если пользователь не соединен, сохранять сообщение в бд
                            synchronized (connections) {
                                Connection messageReceiver = connections.get(Integer.parseInt(data[1]));
                                messageReceiver.out.println(data[2]);
                            }
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
