package MulTic;

public class Board {
    int[][] board;
    int playerRn;

    public Board() {
        board = new int[3][3];
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                board[i][j] = 0;
            }
        }
        playerRn = 1;
    }

    public void printBoard() {
        System.out.println("---------");
        for (int i = 0; i < 3; i++) {
            System.out.print("| ");
            for (int j = 0; j < 3; j++) {
                if (board[i][j] == 0) {
                    System.out.print("  ");
                } else if (board[i][j] == 1) {
                    System.out.print("X ");
                } else {
                    System.out.print("O ");
                }
            }
            System.out.println("|");
        }
        System.out.println("---------");
    }

    @Override
    public String toString() {
        String res = "";
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                res += board[i][j] + " ";
            }
        }
        return res + '|' + playerRn;
    }

    public void setBoard(String str) {
        playerRn = Integer.parseInt(str.substring(str.length() - 1));
        String[] arr = str.split(" ");
        int k = 0;
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                board[i][j] = Integer.parseInt(arr[k]);
                k++;
            }
        }
    }

    public boolean isFull() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (board[i][j] == 0) {
                    return false;
                }
            }
        }
        return true;
    }

    public int isWin() {
        for (int i = 0; i < 3; i++) {
            if (board[i][0] == board[i][1] && board[i][1] == board[i][2] && board[i][0] != 0) {
                return board[i][0];
            }
            if (board[0][i] == board[1][i] && board[1][i] == board[2][i] && board[0][i] != 0) {
                return board[0][i];
            }
        }
        if (board[0][0] == board[1][1] && board[1][1] == board[2][2] && board[0][0] != 0) {
            return board[0][0];
        }
        if (board[0][2] == board[1][1] && board[1][1] == board[2][0] && board[0][2] != 0) {
            return board[0][2];
        }
        return 0;
    }

    public boolean isValidMove(int x, int y) {
        return x >= 0 && x < 3 && y >= 0 && y < 3 && board[x][y] == 0;
    }

    public boolean makeMove(int x, int y, int player) {

        if (isValidMove(x, y) && player == playerRn) {
            board[x][y] = player;
            playerRn = playerRn == 1 ? 2 : 1;
            return true;
        }
        return false;
    }
}
