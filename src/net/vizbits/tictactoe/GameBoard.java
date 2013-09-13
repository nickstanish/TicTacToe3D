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
	 * Nick Stanish
	 */
	private static final long serialVersionUID = 855429676410275612L;
	public static byte X_VALUE = 1;
	public static byte Y_VALUE = -1;
	private int win;
	private Controller control;
	private int y = 40, x = 40;
	private int xWins, oWins = 0;
	private boolean Xturn = true;
	private int[][] winCombos = {
			{0,3,6},{0,1,2},{9,12,15},{9,10,11},{18,21,24},{18,19,20}, // derive?
			{1,4,7},{3,4,5},{10,13,16},{12,13,14},{19,22,25},{21,22,23},
			{2,5,8},{6,7,8},{11,14,17},{15,16,17},{20,23,26},{24,25,26},
			{0,9,18},{3,12,21},{1,10,19},{4,13,22},{2,11,20},{5,14,23},
			{0,4,8},{9,13,17},{18,22,26},{24,22,20},{15,13,11},{6,4,2},
			{0,13,26},{2,13,24},{6,13,20},{8,13,18},{0,10,20},{3,13,23},
			{6,16,26},{2,10,18},{0,12,24},{6,12,18},{5,13,21},{1,13,25},
			{7,13,19},{8,16,24},{2,14,26},{8,14,20},{7,16,25},{6,15,24},
			{8,17,26} };
	private Cell[] cells = new Cell[27];
	private int[] winners = new int[3];
	private int[] cellRank = {7,4,7,4,5,4,7,4,7,4,5,4,5,13,5,4,5,4,7,4,7,4,5,4,7,4,7}; // auto? count each number
	private int ai[] = {0,0,0};
	private boolean winChecked = true;
	private Random rdm = new Random();
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
	    int[] values = {7,4,7,4,5,4,7,4,7,4,5,4,5,13,5,4,5,4,7,4,7,4,5,4,7,4,7};
	    winners[0] = -1;
	    winners[1]= -1;
	    winners[2] = -1;
	    Arrays.fill(ai, 0);
	    
	    for (int p = 0; p < 27; p++){
	        cellRank[p] = values[p];
	        cells[p].reset();
	    }
	    win = 0;
	    winChecked = false;
	    if(control.getGameType() == 1 && !Xturn && win == 0){
	    	moveAI();
	    }
	}
	//***********************************************************
	private void updateAI(){
	    //set ranks
	        for(int i = 0; i < winCombos.length; x++){
	        int a = winCombos[i][0];
	        int b = winCombos[i][1];
	        int c = winCombos[i][2];
	        if(cells[a].value == cells[b].value && cells[a].value != 0){
	            cellRank[c] += 15;
	        }
	        if(cells[a].value == cells[c].value && cells[a].value != 0){
	            cellRank[b] += 15;
	        }
	        if(cells[c].value == cells[b].value && cells[c].value != 0){
	            cellRank[a] += 15;
	        }
	    }
	    for(int i = 0; x < cells.length; x++){
	        if(cells[i].value != 0){
	            cellRank[i] = 0;
	        }
	     }
	}
	private void moveAI(){
	    find3Best();
	    double x = rdm.nextDouble();
	    switch (control.getDifficulty()){
	        case 0:
	            //easy
	            if(x > .95){ //5%
	                //pick best move
	                cells[ai[2]].value = switchTurns();
	            }
	            else if(x > .5){
	                //pick second best move
	                cells[ai[1]].value = switchTurns();
	            }
	            else{
	                //pick third best move
	                cells[ai[0]].value = switchTurns();
	            }
	            break;
	        case 1:
	            //medium
	            if(x > .5){
	                //pick best move
	                cells[ai[2]].value = switchTurns();
	            }
	            else if(x > .05){
	                //pick second best move
	                cells[ai[1]].value = switchTurns();
	            }
	            else{
	                //pick third best move
	                cells[ai[0]].value = switchTurns();
	            }
	            break;
	        case 2:
	            //hard
	            //pick best move
	            cells[ai[2]].value = switchTurns();
	            break;
	            
	    }
	    checkWin();
	}
	private void find3Best(){
	    updateAI();
	    int a = 0;
	    int b = 0;
	    int c = 0;
	    for(int y = 0; y < cellRank.length; y++){
	        if (cellRank[y] > c){
	            ai[0] = ai[1];
	            ai[1] = ai[2];
	            ai[2] = y;
	            a = b;
	            b = c;
	            c = cellRank[y];
	        }
	        else if (cellRank[y] > b){
	            ai[0] = ai[1];
	            ai[2] =  y;
	            a = b;
	            b = cellRank[y];
	        }
	        else if (cellRank[y] > a){
	            ai[0] =  y;
	            a = cellRank[y];
	        }
	    }
	}
	public byte switchTurns(){
		byte number;
	    if (Xturn){
	    	Xturn = false;
	        number = 1; //x
	    }
	    else{
	    	number = -1; //y
	    	Xturn = true;
	    }
	    return number;
    }
	/***********************************************************/
	private int check(){
	/*shortened over 80 lines*/
	/*supposedly checks for every given combination for every grid*/
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
	    if(control.getGameType() == 1 && !Xturn && win == 0){
	        moveAI();
	    }
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
	    for(int c = 0; c<3; c++){
	    	for(int a = 0; a <3; a ++){
	    		for (int b = 0; b < 3; b++) {
	    			int y = 9*c + a + 3*b;
	    			cells[c+a+b] = new Cell(x*(a+1+c), y*(b+1+(4*c)), x,x ,this );
	            }
	    	}
	    }
	    repaintTimer.start();
	    }
	    catch(IOException ie){
	        JOptionPane.showMessageDialog(new JFrame(),
	        		    "Media not found\n" + ie);
	            System.exit(0);
	    }  
	}
//***********************************************************
	public GameBoard(Controller control){
	  	this.control = control;
	   	control.setGameBoard(this);
	  	initComponents();
	  	for(int i = 0; i< 27; i ++){
	  		/*
	  		 * set up initial location of cells::staggered
	  		 */
	  		cells[i] = new Cell(x*(i%3 + i/9 + 1), y*(i/3 + i/9 + 1),x,x,this );
	  	}
	    setPreferredSize(new Dimension(350, 500));
	    setBackground(Color.white);
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
	   
		g.drawImage(background, 0,0,null);
	    g.setColor(Color.BLACK);
	    g.drawImage(null, x, y, null);
	    if( win == 0){
	    	g.drawImage(turnImage, 190,10,null);
	    	if (Xturn){
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
		    		control.xWins();
		    		System.out.println("x: " + xWins + " o: " + oWins);
		    	}
		    	else{
		    		oWins++;
		    		control.oWins();
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
