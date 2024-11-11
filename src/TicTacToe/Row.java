package TicTacToe;

public class Row{

    Node[] nodes;
    public Row() {
        this.nodes = new Node[3];
        this.nodes[0] = new Node();
        this.nodes[1] = new Node();
        this.nodes[2] = new Node();
    }
    public Row(Node[] nodes) {
        this.nodes = nodes;
    }

    public Node GetNode(int num) {
        return this.nodes[num];
    }

    public int GetValue(int num) {
        return this.nodes[num].getPlayer();
    }

    public void setNode(int num, int value) {
        this.nodes[num].setPlayer(value);
    }

    public boolean isLegal(int node) {

        return node >= 0 && node < this.nodes.length && this.nodes[node].IsLegal();
    }

    public boolean isWon(int player) {
        return this.nodes[0].getPlayer() == player &&
                this.nodes[0].getPlayer() == this.nodes[1].getPlayer() &&
                this.nodes[0].getPlayer() == this.nodes[2].getPlayer();
    }

    public void drawRow() {
        char[] players = {'X', 'O'};
        for (int i = 0; i< this.nodes.length - 1; i++) {
            System.out.print("\t" + this.nodes[i].drawNode()+ "\t|");
        }
        System.out.print("\t" + this.nodes[this.nodes.length - 1].drawNode()+ "\t\n");


    }

}
