package com.blogspot.javafilter.tictactoe;

public class Main {
	/*
	 * Nick Stanish
	 */
	public static void main(String[] args) {
		Controller control = new Controller();
		GameBoard board = new GameBoard(control);
		new GameFrame("TicTacToe", board, control);

	}

}
