//gpackage MulTic;
//
//import javafx.application.Application;
//import javafx.application.Platform;
//import javafx.scene.Scene;
//import javafx.scene.control.Button;
//import javafx.scene.layout.GridPane;
//import javafx.stage.Stage;
//
//import java.io.BufferedReader;
//import java.io.IOException;
//import java.io.PrintWriter;
//
//public class MainApp extends Application {
//
//    private static Board board;
//    private boolean isXTurn = true;
//    private static Button[][] buttons = new Button[3][3];
//    private static int player;
//    private BufferedReader in;
//    private PrintWriter out;
//
//    public void setBoard(String boardString, int player) {
//        MainApp.board = new Board();
//        MainApp.board.setBoard(boardString);
//        MainApp.player = player;
//        updateBoardUI();
//    }
//
//    public MainApp getMain() {
//        return this;
//    }
//
//    public void setConnection(PrintWriter out, BufferedReader in) {
//        this.out = out;
//        this.in = in;
//    }
//
//    @Override
//    public void start(Stage primaryStage) throws Exception {
//        GridPane grid = new GridPane();
//
//        for (int row = 0; row < 3; row++) {
//            for (int col = 0; col < 3; col++) {
//                Button button = new Button();
//                button.setMinSize(80, 80);
//                final int r = row;
//                final int c = col;
//                button.setOnAction(event -> handleButtonClick(r, c));
//                buttons[row][col] = button;
//                grid.add(button, col, row);
//            }
//        }
//
//        Scene scene = new Scene(grid, 320, 340);
//        primaryStage.setTitle("MulTac Game");
//        primaryStage.setScene(scene);
//        primaryStage.show();
//    }
//
//    private void handleButtonClick(int row, int col) {
//        try {
//            if (board.getBoard()[row][col] == 0 && board.isValidMove(row, col)) {
//                if (player == 2) {
//                    out.println(row + " " + col);
//                    if (in.readLine().equals("invalid move")) {
//                        System.out.println("invalid move");
//                        return;
//                    }
//                }
//                if (board.makeMove(row, col, player)) {
//                    updateBoardUI();
//                }
//                if (player == 1) {
//                    out.println(board.toString());
//                }
//
//            }
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
//
//    private void updateBoardUI() {
//        Platform.runLater(() -> {
//            for (int i = 0; i < 3; i++) {
//                for (int j = 0; j < 3; j++) {
//                    if (board.getBoard()[i][j] == 1) {
//                        buttons[i][j].setText("X");
//                    } else if (board.getBoard()[i][j] == 2) {
//                        buttons[i][j].setText("O");
//                    } else {
//                        buttons[i][j].setText("");
//                    }
//                }
//            }
//        });
//    }
//
//    private void showWinMessage() {
//        System.out.println("We have a winner!");
//        // Optionally, you can add a dialog or reset the game here
//    }
//
//    public static void main(String[] args) {
//        launch(args);
//    }
//}