package TicTacToe;

public class Node {

    int player;


    public Node() {
        this.player = 0;
    }

    public Node(int player) {
        this.player = player;
    }


    public boolean IsLegal() {
        return this.player == 0;
    }

    public void setPlayer(int value) {
        this.player = value;
    }
    public int getPlayer() {
        return this.player;
    }

    public char drawNode() {

        char[] players = {' ', 'X', 'O'};
        return players[this.player];
    }
}
