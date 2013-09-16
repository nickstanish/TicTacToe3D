package net.vizbits.tictactoe;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.geom.Rectangle2D;

public class Cell extends Rectangle{
	/**
	 * @author: nick stanish
	 */
	private GameBoard game; 
	public byte value = 0; // only needs 3 values so why waste 3 bytes?
	private boolean win = false;
	private Rectangle2D.Double rect;
	private static final long serialVersionUID = -7085470505834557947L;

	public Cell(int x, int y, int width, int height, GameBoard game){
		super(x, y, width, height);
		this.game = game;
		rect = new Rectangle2D.Double(x,y,width,height);
	}
	public boolean contains(Point p){
		return super.contains(p);
	}
	public void toggle(){
		value++;
		setWin();
		if (value > 1) value = -1;
		game.repaint();
	}
	public void reset(){
		value = 0;
		win = false;
	}
	/**
	 * for win highlighting
	 */
	public void setWin(){
		win = true;
	}
	
	public void draw(Graphics2D g){
		// box background color
		g.setColor(win? Color.yellow: Color.white);
		g.fill(rect);
		// draw box outline
		g.setColor(Color.black);
		g.draw(rect);
		// draw shape
		if(value == GameBoard.X_VALUE){
			g.drawImage(game.xImage,x, y, null);
		}
		if(value == GameBoard.Y_VALUE){
			g.drawImage(game.circleImage,x, y,null);
		}
		
        
	}

}
