package MulTic;

import java.io.*;
import java.net.*;
import java.util.*;

public class Client2 {
    private static Socket client;
    private static BufferedReader in;
    private static PrintWriter out;
    private static boolean is_connected = false;
    private static Scanner scanner = new Scanner(System.in);

    private static Board board;
    private static int player;

    private static void connectToClient(String clientIP) {
        try {
            // change the connection to the opponent
            client = new Socket(clientIP, 1235);
            in = new BufferedReader(new InputStreamReader(client.getInputStream()));
            out = new PrintWriter(client.getOutputStream(), true);
            System.out.println("Connected to the opponent");

            /// now its time to start the game!
            board = new Board();
            String[] message;
            boolean didMake = false;
            while (is_connected && board.isWin() == 0 && !board.isFull()) {
                // get this client input
                String b = in.readLine();
                board.setBoard(b);
                board.printBoard();
                do {
                    String newBoard = in.readLine();
                    board.setBoard(newBoard);
                    System.out.println(board.playerRn + "'s turn: ");
                    board.printBoard();
                    if (board.isWin() != 0 || board.isFull()) {
                        break;
                    }
                    message = scanner.nextLine().split(" ");
                    if (message[0].equals("exit")) {
                        out.println("exit");
                        is_connected = false;
                        break;
                    } else {
                        if (message.length < 2) {
                            System.out.println("invalid move");
                            continue;
                        }
                        didMake = board.makeMove(Integer.parseInt(message[0]), Integer.parseInt(message[1]), 2);
                        if (!didMake) {
                            System.out.println("invalid move");
                        }
                        else {
                            out.println(message[0] + " " + message[1]);
                        }
                        if (in.readLine().equals("invalid move")) {
                            System.out.println("invalid move");
                            didMake = false;
                        } else {
                            didMake = true;
                        }
                    }
                } while (!didMake);
            }
//            String message;
//            while (is_connected && (message = in.readLine()) != null) {
//                System.out.println(message);
//            }
            System.out.println(in.readLine());
//            if (board.isWin() == 2) {
//                out.println("You lose!");
//                System.out.println("You Win!");
//            } else if (board.isWin() == 1) {
//                out.println("You win!");
//                System.out.println("You Lose!");
//            } else {
//                out.println("Draw!");
//                System.out.println("Draw!");
//            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private static void becomeTheServer() {
        try {
            ServerSocket server = new ServerSocket(1235);
            Socket client = server.accept();
            in = new BufferedReader(new InputStreamReader(client.getInputStream()));
            out = new PrintWriter(client.getOutputStream(), true);
            System.out.println("Connected to the opponent");
            /// now its time to start the game!
            board = new Board();
            // send the board to the client


            player = 0;
            boolean did_make = false;
            String[] message;
            while (is_connected && board.isWin() == 0 && !board.isFull()) {


                // get this client input
                System.out.println(board.playerRn + "'s turn");
                out.println(board.toString());
                board.printBoard();

                do {
                    String clientMessage = scanner.nextLine();
                    message = clientMessage.split(" ");
                    if (message[0].equals("exit")) {
                        is_connected = false;
                        break;
                    } else {
                        did_make = board.makeMove(Integer.parseInt(message[0]), Integer.parseInt(message[1]), 1);
                        if (!did_make) {
                            System.out.println("invalid move");
                        }
                    }
                } while (!did_make);
                // get this client input
                System.out.println(board.playerRn + "'s turn");

                out.println(board.toString());
                board.printBoard();

                did_make = false;
                if (!is_connected || board.isWin() != 0 || board.isFull()) {
                    break;
                }
                do {
                    // get other client input
                    message = in.readLine().split(" ");

                    if (message[0].equals("exit")) {
                        is_connected = false;
                        break;
                    } else {
                        did_make = board.makeMove(Integer.parseInt(message[0]), Integer.parseInt(message[1]), 2);
                        if (!did_make) {
                            out.println("invalid move");
                        }
                    }
                }while (!did_make);
            }
            out.println(board.toString());

            if (board.isWin() == 1) {
                out.println("You lose!");
                System.out.println("You Win!");
            } else if (board.isWin() == 2) {
                out.println("You win!");
                System.out.println("You Lose!");
            } else {
                out.println("Draw!");
                System.out.println("Draw!");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        String message = null;
        String response = null;
        try {
            client = new Socket("localhost", 1234);
            in = new BufferedReader(new InputStreamReader(client.getInputStream()));
            out = new PrintWriter(client.getOutputStream(), true);
            is_connected = true;
//            Thread messageThread = new Thread(() -> getMessages());
//            messageThread.start();

            while (is_connected) {
                // get input from console
                message = scanner.nextLine();

                out.println(message);
                if (message.equals("exit")) {
                    is_connected = false;
                    break;
                }
                response = in.readLine();
                System.out.println(response);

//                messageThread.join();
                break;

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println(message);
        System.out.println("this is:" + response.split(" ")[0]);
        System.out.println("and this: " + response.split(" ")[0].equals("SentIP"));
        if (response.split(" ")[0].equals("SentIP")) {
            System.out.println("Connecting to the opponent...");
            connectToClient(response.split("/")[1]);
        } else {
            System.out.println("Waiting for the opponent...");
            becomeTheServer();
        }
        System.out.println("Game Over");

    }

//    s
}
