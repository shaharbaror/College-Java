package MultiClientServer;

import java.io.*;
import java.net.*;
import java.util.*;

public class server {
    private static final int PORT = 1234;
    private static ServerSocket server;
    private static HashMap<String, Socket> clients = new HashMap<String, Socket>();
    private static HashMap<Socket, Thread> threads = new HashMap<Socket, Thread>();

    private static synchronized boolean addClient(String name, Socket client) {
        if (!clients.containsKey(name)) {
            clients.put(name, client);
            return true;
        }
        return false;

    }


    private static String findClient(Socket client) {
        for (Map.Entry<String, Socket> entry : clients.entrySet()) {
            if (entry.getValue() == client) {
                return entry.getKey();
            }
        }
        return null;
    }

    private static synchronized void removeClient(Socket client) {
        String name = findClient(client);
        clients.remove(name);

        try {
            threads.get(client).interrupt();
            threads.remove(client);
            client.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void ClientHandler(Socket client) {

        try {
            BufferedReader in = new BufferedReader(new InputStreamReader(client.getInputStream()));
            PrintWriter out = new PrintWriter(client.getOutputStream(), true);
            boolean is_added = false;
            // Get The client's username and add it to the clients list
            do {
                out.println("Enter your name: ");
                String name = in.readLine();
                System.out.println(name);
                is_added = addClient(name, client);
                if (is_added) {
                    out.println("Welcome to the chat room, " + name);
                    break;
                } else {
                    out.println("This name is already taken.");
                }
            } while (!is_added);

            while (true) {
                String message = in.readLine();
                if (message.equals("exit")) {
                    removeClient(client);
                    break;
                }
                broadcast(message, client, findClient(client));

            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static synchronized void broadcast(String message, Socket client, String username) {
        for (Socket c : clients.values()) {
            if (c != client) {
                try {
                    PrintWriter out = new PrintWriter(c.getOutputStream(), true);
                    // send message to all clients except the sender with username
                    out.println("[" + username + "]: " + message);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }




    public static void main(String[] args) {
        try {
            server = new ServerSocket(PORT);
            System.out.println("Server is running on port " + PORT);

                while (true) {
                    Socket client = server.accept();
                    System.out.println("New client connected: " + client);

                    Thread t = new Thread(() -> {ClientHandler(client);});
                    threads.put(client, t);
                    t.start();
                }
            } catch (IOException e) {
                e.printStackTrace();
        }
    }

}
