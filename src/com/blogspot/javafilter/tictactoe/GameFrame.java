package com.blogspot.javafilter.tictactoe;

import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class GameFrame extends JFrame{
	/**
	 * Nick Stanish
	 */
	private static final long serialVersionUID = 3384368516552751571L;

	public GameFrame(String title, GameBoard board, Controller control){
		super(title);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        JPanel mainpanel = new JPanel();
        mainpanel.setBackground(Color.lightGray);
        mainpanel.setMinimumSize(new Dimension(500,500));
        mainpanel.setMaximumSize(new Dimension(500,500));
        mainpanel.add(board);
        mainpanel.add(control);
        setResizable(false);
        add(mainpanel);
        pack();
        setVisible(true);
	}

}
