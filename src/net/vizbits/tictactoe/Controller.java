package net.vizbits.tictactoe;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;

public class Controller extends JPanel {

    /**
     * @author: Nick Stanish
     */
    /**
     * todo: redo../cleanup
     */
    private static final long serialVersionUID = -6715952786491926010L;
    private GameBoard board;
    private ButtonGroup buttongroup, difficultyGroup;
    private JRadioButton opt1, opt2, easy, medium, hard;
    private JLabel x, o, options, wins;
    private JButton resetButton, newButton;
    private JPanel[] pane = new JPanel[5];
    private int xWins, oWins = 0;
    File file;
    Image xImage, oImage;
    ImageIcon xIcon, oIcon;

    public Controller() {
        setPreferredSize(new Dimension(150, 500));
        setBackground(Color.lightGray);
        initComponents();
    }

    public void setGameBoard(GameBoard board) {
        this.board = board;
    }

    public void xWins() {
        xWins++;
        x.setText(": " + xWins);
    }

    public void oWins() {
        oWins++;
        o.setText(": " + oWins);
    }

    private void initComponents() {
        try {
            file = new File("media/xIcon.png");
            xImage = ImageIO.read(file);
            file = new File("media/oIcon.png");
            oImage = ImageIO.read(file);
        } catch (IOException ie) {
            JOptionPane.showMessageDialog(new JFrame(),
                    "Media not found\n" + ie);
            System.exit(0);
        }
        xIcon = new ImageIcon(xImage);
        oIcon = new ImageIcon(oImage);
        resetButton = new JButton("Reset");
        resetButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                resetButtonActionPerformed();
            }
        });
        newButton = new JButton("New Game");
        newButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                newButtonActionPerformed();
            }
        });
        buttongroup = new ButtonGroup();
        difficultyGroup = new ButtonGroup();
        opt1 = new JRadioButton("2 Player");
        opt1.setBackground(Color.lightGray);
        opt1.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                opt1ActionPerformed();
            }
        });
        opt2 = new JRadioButton("1 Player");
        opt2.setBackground(Color.lightGray);
        opt2.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                opt2ActionPerformed();
            }
        });
        easy = new JRadioButton("Easy");
        easy.setBackground(Color.lightGray);
        medium = new JRadioButton("Medium");
        medium.setBackground(Color.lightGray);
        medium.setSelected(true);
        hard = new JRadioButton("Hard");
        hard.setBackground(Color.lightGray);
        difficultyGroup.add(easy);
        difficultyGroup.add(medium);
        difficultyGroup.add(hard);
        buttongroup.add(opt1);
        buttongroup.add(opt2);
        opt1.setSelected(true);
        x = new JLabel(": 0");
        o = new JLabel(": 0");
        o.setIcon(oIcon);
        x.setIcon(xIcon);
        options = new JLabel("Options");
        wins = new JLabel("WINS        ");
        for (int z = 0; z < 5; z++) {
            pane[z] = new JPanel();
            pane[z].setBackground(Color.lightGray);
        }
        pane[0].setLayout(new BoxLayout(pane[0], BoxLayout.PAGE_AXIS));
        pane[0].add(Box.createRigidArea(new Dimension(0, 10)));
        pane[0].add(wins);
        pane[0].add(Box.createRigidArea(new Dimension(0, 4)));
        pane[0].add(x);
        pane[0].add(o);
        pane[0].add(Box.createRigidArea(new Dimension(0, 6)));
        pane[0].add(resetButton);
        pane[0].add(Box.createRigidArea(new Dimension(0, 20)));
        pane[1].setLayout(new BoxLayout(pane[1], BoxLayout.PAGE_AXIS));
        pane[1].add(Box.createRigidArea(new Dimension(0, 100)));
        pane[1].add(options);
        pane[1].add(Box.createRigidArea(new Dimension(0, 6)));
        pane[1].add(opt1);
        pane[1].add(opt2);
        pane[2].setLayout(new BoxLayout(pane[2], BoxLayout.PAGE_AXIS));
        pane[2].setBorder(BorderFactory.createEmptyBorder(0, 15, 0, 0));
        pane[2].add(easy);
        pane[2].add(medium);
        pane[2].add(hard);
        pane[1].add(pane[2]);
        pane[2].setVisible(false);
        pane[1].add(Box.createRigidArea(new Dimension(0, 6)));
        pane[1].add(newButton);
        add(pane[0]);
        add(pane[1]);

    }

    private void opt1ActionPerformed() {
        pane[2].setVisible(false);
    }

    private void opt2ActionPerformed() {
        pane[2].setVisible(true);
    }

    private void resetButtonActionPerformed() {
        xWins = 0;
        oWins = 0;
        x.setText(": " + xWins);
        o.setText(": " + oWins);
    }

    private void newButtonActionPerformed() {
        board.Clear();
    }

    public int getGameType() {
        int x;
        if (opt1.isSelected()) {
            x = 0;
        } else {
            x = 1;
        }
        return x;
    }

    public int getDifficulty() {
        int x = -1;
        if (easy.isSelected()) {
            x = 0;
        }
        if (medium.isSelected()) {
            x = 1;
        }
        if (hard.isSelected()) {
            x = 2;
        }
        return x;
    }
}
