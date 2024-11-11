package TicTacToe;

import java.util.Scanner;

public class Main {

    static void doMove(Board board, int player, Scanner scanner) {
        int x,y;
        boolean isLegalMove;

        do {
            System.out.println("Enter a row: ");
            y = scanner.nextInt() - 1;
            System.out.println("Enter a column: ");
            x = scanner.nextInt() - 1;
            isLegalMove = board.setValue(y, x, player);
            if (!isLegalMove) System.out.println("Please Enter a valid row and column");

        }while (!isLegalMove);
    }


    public static void main(String[] args) {
        Board board = new Board();
        int player = 0;
        Scanner scanner = new Scanner(System.in);
        do{
            board.drawBoard();
            doMove(board, player + 1, scanner);
            player = (player + 1)% 2;
        }while (!board.checkIfPlayerWon(1) && !board.checkIfPlayerWon(2));
    }
}