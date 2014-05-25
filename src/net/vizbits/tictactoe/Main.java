package net.vizbits.tictactoe;

public class Main {
    /*
     * @author: Nick Stanish
     */

    public static void main(String[] args) {
        Controller control = new Controller();
        GameBoard board = new GameBoard(control);
        new GameFrame("TicTacToe", board, control);

    }

}
