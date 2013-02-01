package com.blogspot.javafilter.tictactoe;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
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
	private int win;
	private Controller control;
	private int y = 40, x = 40;
	private int xWins, oWins = 0;
	private boolean Xturn = true;
	private int[][] winCombos = {
			{0,3,6},{0,1,2},{9,12,15},{9,10,11},{18,21,24},{18,19,20},
			{1,4,7},{3,4,5},{10,13,16},{12,13,14},{19,22,25},{21,22,23},
			{2,5,8},{6,7,8},{11,14,17},{15,16,17},{20,23,26},{24,25,26},
			{0,9,18},{3,12,21},{1,10,19},{4,13,22},{2,11,20},{5,14,23},
			{0,4,8},{9,13,17},{18,22,26},{24,22,20},{15,13,11},{6,4,2},
			{0,13,26},{2,13,24},{6,13,20},{8,13,18},{0,10,20},{3,13,23},
			{6,16,26},{2,10,18},{0,12,24},{6,12,18},{5,13,21},{1,13,25},
			{7,13,19},{8,16,24},{2,14,26},{8,14,20},{7,16,25},{6,15,24},
			{8,17,26} };
	private int[] cell = new int[27];
	private int[] winners = new int[3];
	private int[] cellRank = {7,4,7,4,5,4,7,4,7,4,5,4,5,13,5,4,5,4,7,4,7,4,5,4,7,4,7};
	private int ai[] = {0,0,0};
	private boolean winChecked = true;
	private Random rdm = new Random();
	private File file;
	private BufferedImage background, xImage, circleImage, turnImage, winImage, xIcon, oIcon, refresh, highlightImage;
	
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
	    Arrays.fill(cell, 0);
	    for (int p = 0; p < 27; p++){
	        cellRank[p] = values[p];
	        System.out.print(cellRank[p]);
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
	        for(int x = 0; x < winCombos.length; x++){
	        int a = winCombos[x][0];
	        int b = winCombos[x][1];
	        int c = winCombos[x][2];
	        if(cell[a] == cell[b] && cell[a] != 0){
	            cellRank[c] += 15;
	        }
	        if(cell[a] == cell[c] && cell[a] != 0){
	            cellRank[b] += 15;
	        }
	        if(cell[c] == cell[b] && cell[c] != 0){
	            cellRank[a] += 15;
	        }
	    }
	    for(int x = 0; x < cell.length; x++){
	        if(cell[x] != 0){
	            cellRank[x] = 0;
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
	                cell[ai[2]] = switchTurns();
	            }
	            else if(x > .5){
	                //pick second best move
	                cell[ai[1]] = switchTurns();
	            }
	            else{
	                //pick third best move
	                cell[ai[0]] = switchTurns();
	            }
	            break;
	        case 1:
	            //medium
	            if(x > .5){
	                //pick best move
	                cell[ai[2]] = switchTurns();
	            }
	            else if(x > .05){
	                //pick second best move
	                cell[ai[1]] = switchTurns();
	            }
	            else{
	                //pick third best move
	                cell[ai[0]] = switchTurns();
	            }
	            break;
	        case 2:
	            //hard
	            //pick best move
	            cell[ai[2]] = switchTurns();
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
	public int switchTurns(){
		int number;
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
	        if(cell[a] == cell[b] && cell[b] == cell[c] && cell[a] != 0){
	            win = cell[a];
	            winners[0] = a;
	            winners[1] = b;
	            winners[2] = c;
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
	        int mouseX = e.getPoint().x;
	        int mouseY = e.getPoint().y;
	        if (win == 0){
	            for (int c =0; c < 3; c++){
	                if (mouseX > (x * (c + 1)) && mouseX < (x * (c + 4)) && mouseY > (y * (1+ (4*c))) && mouseY < (y*(4*(c+1)))){
	                    for (int a = 0; a <3; a++){
	                        for (int b = 0; b <3; b++){
	                            if ((mouseX > ((a+1 +c) * x)) && (mouseX < ((a+2 + c)*x)) && (mouseY > ((b+1+(4*c))*y)) && (mouseY < ((b+2+(4*c))*y) )){
	                                int y = 9*c + a + 3*b;
	                                if(cell[y] == 0){
	                                    cell[y] = switchTurns();
	                                }
	                            }
	                        }
	                    }
	                }
	            }
	            checkWin();
	            repaint();
	        }
	        else{
	            if (mouseX > 230 && mouseX < 270 && mouseY > 50 && mouseY < 90){
	                Clear();
	                
	            }
	        }
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
	public GameBoard(Controller control){
	  	this.control = control;
	   	control.setGameBoard(this);
	  	initComponents();
	    setPreferredSize(new Dimension(350, 500));
	    setBackground(Color.lightGray);
	    addMouseListener(new GameListener());
	    Clear();
	}
	@Override
	public void paintComponent (Graphics g){
		super.paintComponent(g);
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
	    for(int c = 0; c<3; c++){
	    	for(int a = 0; a <3; a ++){
	    		for (int b = 0; b < 3; b++) {
	    			int y = 9*c + a + 3*b;
	                if(winners[0] == y || winners[1] == y || winners[2] == y){
	                	g.drawImage(highlightImage,x*(a+1+c),x*(b+1+(4*c)),null);
	                }
	                if (cell[y] == 1){
	                	g.drawImage(xImage,x*(a+1+c),x*(b+1+(4*c)),null);
	                }
	                if (cell[y] == -1){
	                    g.drawImage(circleImage,x*(a+1+c),x*(b+1+(4*c)),null);
	                }
	            }
	    	}
	    }
	    g.drawLine(160,160,240,480);
	}   
}
