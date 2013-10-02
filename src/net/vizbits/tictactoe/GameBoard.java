package net.vizbits.tictactoe;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class GameBoard extends JPanel{
	
	/**
	 * @author: Nick Stanish
	 */
	public static Color BGCOLOR = Color.lightGray;
	private static final long serialVersionUID = 855429676410275612L;
	public static byte X_VALUE = 1;
	public static byte Y_VALUE = -1;
	private int win;
	private Controller controller;
	public static int height = 40, width = 40;
	private int xWins, oWins = 0;
	private boolean turnX = true;
	private AI ai;
	public static int[][] winCombos = {
			{0,3,6},{0,1,2},{9,12,15},{9,10,11},{18,21,24},{18,19,20}, // derive?
			{1,4,7},{3,4,5},{10,13,16},{12,13,14},{19,22,25},{21,22,23},
			{2,5,8},{6,7,8},{11,14,17},{15,16,17},{20,23,26},{24,25,26},
			{0,9,18},{3,12,21},{1,10,19},{4,13,22},{2,11,20},{5,14,23},
			{0,4,8},{9,13,17},{18,22,26},{24,22,20},{15,13,11},{6,4,2},
			{0,13,26},{2,13,24},{6,13,20},{8,13,18},{0,10,20},{3,13,23},
			{6,16,26},{2,10,18},{0,12,24},{6,12,18},{5,13,21},{1,13,25},
			{7,13,19},{8,16,24},{2,14,26},{8,14,20},{7,16,25},{6,15,24},
			{8,17,26} };
	public Cell[] cells = new Cell[27];
	private boolean winChecked = true;
	private Random random = new Random();
	private File file;
	public BufferedImage background, xImage, circleImage, turnImage, winImage, xIcon, oIcon, refresh, highlightImage;
	
	/************************************************************
	 * Repaint timer: 800 ms
	 ************************************************************/
	javax.swing.Timer repaintTimer = new javax.swing.Timer(800, new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			repaint();
		}
	});
	//***********************************************************
	public void Clear(){
	    for (int p = 0; p < 27; p++){
	        cells[p].reset();
	    }
	    ai.reset();
	    win = 0;
	    winChecked = false;
	    moveAI();
	}
	private void moveAI(){
		if(controller.getGameType() == 1 && !turnX && win == 0){
			System.out.print(">>AI");
			int c = 0;
			/*
			 * fixes bug where ai picks a location that is already taken... which shouldnt have been possible..
			 */
			do{
				c = ai.getMove();
				System.out.println("\t" + c);
			}
			while(cells[c].value != 0);
	    	cells[c].value = switchTurns();
	    	checkWin();
	    }
	}
	/**
	 * switch boolean turns
	 * @return turn it is: xvalue or yvalue
	 */
	public byte switchTurns(){
		byte number = turnX ? X_VALUE : Y_VALUE;
	    turnX = !turnX;
	    return number;
    }
	/***********************************************************/
	private int check(){
	/*should check for every given combination for every grid*/
	    for(int x = 0; x < winCombos.length; x++){
	        int a = winCombos[x][0];
	        int b = winCombos[x][1];
	        int c = winCombos[x][2];
	        if(cells[a].value == cells[b].value && cells[b].value == cells[c].value && cells[a].value != 0){
	            win = cells[a].value;
	            cells[a].setWin();
	            cells[b].setWin();
	            cells[c].setWin();
	        }
	    }
	return win;
	}
	/***********************************************************/
	public void checkWin(){
	    win = check();
	    winChecked = false;
	    System.out.println("win = " + win);
	    moveAI();
	}
	/***********************************************************/
	class GameListener implements MouseListener {
	    public void mouseClicked(MouseEvent e){}
	    public void mousePressed (MouseEvent e){
	    	for(Cell c: cells){
	        	if(win == 0 && c.contains(e.getPoint())){
	        		if(c.value == 0){
	        			c.value = switchTurns();
	        			checkWin();
	        		}	
	        	}
	        }
	        Rectangle clear = new Rectangle(230,50,40,40);
	        if (clear.contains(e.getPoint())) Clear();
	        repaint();
	    }
	    public void mouseReleased(MouseEvent e){}
	    public void mouseEntered(MouseEvent e){}
	    public void mouseExited (MouseEvent e){}
	}
	private void initComponents(){
	    try{
	    file = new File("media/background.png");
	    background = ImageIO.read(file);
	    file = new File("media/circle1.png");
	    circleImage = ImageIO.read(file);
	    file = new File("media/x1.png");
	    xImage = ImageIO.read(file);
	    file = new File("media/turn.png");
	    turnImage = ImageIO.read(file);
	    file = new File("media/win.png");
	    winImage = ImageIO.read(file);
	    file = new File("media/xIcon.png");
	    xIcon = ImageIO.read(file);
	    file = new File("media/oIcon.png");
	    oIcon = ImageIO.read(file);
	    file = new File("media/refresh.png");
	    refresh = ImageIO.read(file);
	    file = new File("media/highlight.png");
	    highlightImage = ImageIO.read(file);
	    repaintTimer.start();
	    }
	    catch(IOException ie){
	        JOptionPane.showMessageDialog(new JFrame(),
	        		    "Media not found\n" + ie);
	            System.exit(0);
	    }  
	}
//***********************************************************
	public GameBoard(Controller controller){
	  	this.controller = controller;
	   	this.controller.setGameBoard(this);
	  	initComponents();
	  	for(int i = 0; i< 27; i ++){
	  		/*
	  		 * set up initial location of cells::staggered
	  		 */
	  		cells[i] = new Cell(width*(i%3 + i/9 + 1), height*(i/3 + i/9 + 1),width,height,this, (byte)i);
	  	}
	  	ai = new AI(this, controller);
	    setPreferredSize(new Dimension(350, 500));
	    setBackground(BGCOLOR);
	    addMouseListener(new GameListener());
	    Clear();
	}
	/*
	 * cleanup
	 */
	@Override
	public void paintComponent (Graphics g2){
		super.paintComponent(g2);
		
		Graphics2D g = (Graphics2D)g2;
		g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		/*
		 * clear screen
		 */
	    g.setColor(BGCOLOR);
	    g.fillRect(0, 0, getWidth(), getHeight());
	    /*
	     * draw background
	     */
		g.drawImage(background, 0,0,null);
	    g.setColor(Color.BLACK);
	    /*
	     * display turnX or win in notification area
	     */
	    if( win == 0){
	    	g.drawImage(turnImage, 190,10,null);
	    	if (turnX){
	    		g.drawImage(xIcon, 280,10,null);
	    	}
	    	else{
	    		g.drawImage(oIcon, 280,10,null);
	    	}
	    }
	    else {
	    	g.drawImage(winImage, 190,10,null);
	    	g.drawImage(refresh, 230, 50, null);
	    	if(!winChecked){
		    	if (win == 1){
		    		xWins++;
		    		controller.xWins();
		    		System.out.println("x: " + xWins + " o: " + oWins);
		    	}
		    	else{
		    		oWins++;
		    		controller.oWins();
		    		System.out.println("x: " + xWins + " o: " + oWins);
		    	}
		    	winChecked = true;
	    	}
	    	if (win == 1){
		    	g.drawImage(xIcon, 280,10,null);
		    }
		    else{
		    	g.drawImage(oIcon, 280,10,null);
		    }
	    	
	    }
	    
	    for(Cell c: cells){
	    	c.draw(g);
	    }
	   g.setColor(Color.black);
	   g.drawLine(160,160,240,480);
	}   
}
