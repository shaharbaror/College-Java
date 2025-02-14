package MulTic;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.PrintWriter;
import java.net.Socket;

public class MainApp extends Application {

    private static Board board;
    private boolean isXTurn = true;
    private Button[][] buttons = new Button[3][3];
    private static int player;
    private BufferedReader in;
    private PrintWriter out;

    public static void setBoard(String board, int player) {
        MainApp.board.setBoard(board);
        MainApp.player = player;
    }
    public void setConnection(PrintWriter out, BufferedReader in) {
        this.out = out;
        this.in = in;
    }


    @Override
    public void start(Stage primaryStage) {
        GridPane grid = new GridPane();

        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 3; col++) {
                final int r = row;
                final int c = col;
                Button button = new Button(board.getCell(r, c));
                button.setMinSize(100, 100);
                button.setOnAction(e -> handleButtonClick(button, r, c));
                buttons[r][c] = button;
                grid.add(button, c, r);
            }
        }

        Scene scene = new Scene(grid, 300, 300);
        primaryStage.setTitle("Tic-Tac-Toe");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    // this method will be called when a button is clicked
    private void handleButtonClick(Button button, int row, int col) {
        boolean isLegal;
        try {
            do {
                isLegal = true;
                if (button.getText().isEmpty() && board.isValidMove(row, col)) {

                    // send move to the opponent
                    out.println(row + " " + col);
                    if (in.readLine().equals("invalid move")) {
                        isLegal = false;
                        System.out.println("invalid move");
                        // show on bottom of the screen
                        continue;
                    }

                    String playerSymbol = player == 1 ? "X" : "O";
                    button.setText(playerSymbol);
                    board.makeMove(row, col, player);
                    board.playerRn = player == 1 ? 2 : 1;

                    checkWinCondition();
                }
            } while (!isLegal);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void checkWinCondition() {
        if (board.isWin() != 0) {
            showWinMessage();
        }
    }

    private void showWinMessage() {
        System.out.println("We have a winner!");
        // Optionally, you can add a dialog or reset the game here
    }

    public static void main(String[] args) {
        launch(args);
    }
}