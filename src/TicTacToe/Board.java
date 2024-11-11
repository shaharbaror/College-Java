package TicTacToe;

public class Board {
    Row[] rows;
    public Board() {
        rows = new Row[3];
        rows[0] = new Row();
        rows[1] = new Row();
        rows[2] = new Row();
    }

    public Board(Row[] rows) {
        this.rows = rows;
    }

    public int getValue(int row, int node) {
        return this.rows[row].GetValue(node);
    }

    public boolean IsLegal(int row, int node) {
        return row >= 0 && row < this.rows.length &&  this.rows[row].isLegal(node);
    }


    public boolean setValue(int row, int node, int value) {
        if (this.IsLegal(row, node)) {
            this.rows[row].setNode(node, value);
            return true;
        } else {
            return false;
        }
    }

    boolean checkIfPlayerWon(int player) {
        char[] players = {' ', 'X', 'O'};
        int firstNodeNum;
        int[][][] winCons = {   {{0,0},{0,1},{0,2}},
                {{1,0},{1,1},{1,2}},
                {{2,0},{2,1},{2,2}},
                {{0,0},{1,0},{2,0}},
                {{0,1},{1,1},{2,1}},
                {{0,2},{1,2},{2,2}},
                {{0,0},{1,1},{2,2}},
                {{2,0},{1,1},{0,2}}};
        for (int i =0; i< winCons.length; i++) {
            firstNodeNum = this.getValue(winCons[i][0][0], winCons[i][0][1]);
            if (firstNodeNum == player &&
                    firstNodeNum == this.getValue(winCons[i][1][0], winCons[i][1][1]) &&
                    firstNodeNum == this.getValue(winCons[i][2][0],winCons[i][2][1])) {
                System.out.println(players[player] + " WON!");
                return true;
            }

        }
        return false;
    }

    public void drawBoard() {
        for (int i =0; i < this.rows.length-1; i++) {
            this.rows[i].drawRow();
            System.out.println("-----------------------");
        }
        this.rows[this.rows.length - 1].drawRow();
    }
}
