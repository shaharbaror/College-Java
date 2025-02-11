package MulTic;

import java.io.*;
import java.net.*;
import java.util.*;

public class DirectionServer {
    private static final int PORT = 1234;
    private static ServerSocket server;
    // made for quickplay
    private static Queue<String> clients = new LinkedList<String>();
    // made for rooms
    private static HashMap<String, String> room = new HashMap<String, String>();

    private static synchronized String addClientQuickPlay(String clientIP) {
        if (clients.isEmpty()) {
            clients.add(clientIP);
        } else {
                return clients.poll();
        }
        return null;
    }

    private static synchronized String addClientRoom(String clientIP, String roomName) {
        if (!room.containsKey(roomName)) {
            room.put(roomName, clientIP);
            return "";
        } else {
            return room.remove(roomName);
        }
    }

    private static synchronized void removeClient(Socket client) {
        try {
            client.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void ClientHandler(Socket client) {

        try {
            BufferedReader in = new BufferedReader(new InputStreamReader(client.getInputStream()));
            PrintWriter out = new PrintWriter(client.getOutputStream(), true);
            String opponent;
            // Get The client's username and add it to the clients list

            String name = in.readLine();
            System.out.println(name);

            // in case of quickplay
            if (name.equals("quickplay")) {

                opponent = addClientQuickPlay(client.getInetAddress().toString());
                // add a client to room
                if (opponent == null) {
                    out.println("Waiting for another player...");
                    return;
                }
                // ---------------------------------

                out.println("SentIP " + opponent);
                // -------------------------------
                removeClient(client);
                return;
            } else if (name.equals("room")) {
                /// in case of room
                String roomName = in.readLine();
                String opponentIP = addClientRoom(client.getInetAddress().toString(), roomName);
                if (Objects.equals(opponentIP, "")) {
                    out.println("Waiting for another player...");
                    removeClient(client);
                    return;
                }
                out.println("SentIP " + opponentIP);
                removeClient(client);
                return;

            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

//    private static synchronized void broadcast(String message, Socket client, String username) {
//        for (Socket c : clients.values()) {
//            if (c != client) {
//                try {
//                    PrintWriter out = new PrintWriter(c.getOutputStream(), true);
//                    // send message to all clients except the sender with username
//                    out.println("[" + username + "]: " + message);
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            }
//        }
//    }




    public static void main(String[] args) {
        try {
            server = new ServerSocket(PORT);
            System.out.println("Server is running on port " + PORT);

            while (true) {
                Socket client = server.accept();
                System.out.println("New client connected: " + client);

                Thread t = new Thread(() -> {ClientHandler(client);});
                t.start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
