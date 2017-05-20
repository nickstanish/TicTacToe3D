package net.vizbits.tictactoe;

/**
 * 
 * @author Nick Stanish
 *
 */
public class Main {

    public static void main(String[] args) {
        Controller control = new Controller();
        GameBoard board = new GameBoard(control);
        new GameFrame("TicTacToe", board, control);

    }

}
