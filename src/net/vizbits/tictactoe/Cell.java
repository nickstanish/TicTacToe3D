package net.vizbits.tictactoe;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.geom.Rectangle2D;

public class Cell extends Rectangle {

    /**
     * @author: nick stanish
     */
    public static Color HIGHLIGHTCOLOR = Color.yellow;
    public static Color BGCOLOR1 = Color.white;
    public static Color BGCOLOR2 = Color.cyan;
    private byte num = 0;
    private GameBoard game;
    public byte value = 0; // only needs 3 values so why waste 3 bytes?
    private boolean win = false;
    private Rectangle2D.Double rect;
    private static final long serialVersionUID = -7085470505834557947L;

    public Cell(int x, int y, int width, int height, GameBoard game, byte number) {
        super(x, y, width, height);
        this.game = game;
        this.num = number;
        rect = new Rectangle2D.Double(x, y, width, height);
    }

    public boolean contains(Point p) {
        return super.contains(p);
    }

    public void reset() {
        value = 0;
        win = false;
    }

    /**
     * for win highlighting
     */
    public void setWin() {
        win = true;
    }

    public void draw(Graphics2D g) {
        // box background color
        if (win) {
            g.setColor(HIGHLIGHTCOLOR);
        } else {
            g.setColor((num % 9) % 2 == 0 ? BGCOLOR1 : BGCOLOR2);
        }
        g.fill(rect);
        // draw box outline
        g.setColor(Color.black);
        g.draw(rect);
        // draw shape
        if (value == GameBoard.X_VALUE) {
            g.drawImage(game.xImage, x, y, null);
        }
        if (value == GameBoard.Y_VALUE) {
            g.drawImage(game.circleImage, x, y, null);
        }

    }

}
