package MultiClientServer;

import java.io.*;
import java.net.*;
import java.util.*;

public class client1 {
    private static Socket client;
    private static BufferedReader in;
    private static PrintWriter out;
    private static boolean is_connected = false;
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        try {
            client = new Socket("localhost", 1234);
            in = new BufferedReader(new InputStreamReader(client.getInputStream()));
            out = new PrintWriter(client.getOutputStream(), true);
            is_connected = true;
            Thread messageThread = new Thread(() -> getMessages());
            messageThread.start();
            String message;
            while (is_connected) {
                // get input from console
                message = scanner.nextLine();


                if (message.equals("exit")) {
                    out.println("exit");
                    is_connected = false;
                    break;
                }
                out.println(message);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void getMessages() {
        try {
            String message;
            while (is_connected && (message = in.readLine()) != null) {
                System.out.println(message);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
