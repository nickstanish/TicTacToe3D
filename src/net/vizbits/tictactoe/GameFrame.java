package net.vizbits.tictactoe;

import java.awt.Color;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class GameFrame extends JFrame {

    /**
     * @author: Nick Stanish
     */
    private static final long serialVersionUID = 3384368516552751571L;

    public GameFrame(String title, GameBoard board, Controller control) {
        super(title);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        JPanel mainpanel = new JPanel();
        mainpanel.setBackground(Color.lightGray);
        mainpanel.add(board);
        mainpanel.add(control);
        this.add(mainpanel);
        this.pack();
        this.setVisible(true);
    }

}
