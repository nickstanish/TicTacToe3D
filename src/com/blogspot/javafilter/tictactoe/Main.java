
package com.blogspot.javafilter.tictactoe;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Random;
import javax.imageio.ImageIO;
import javax.swing.*;

/**
 * @author Nick STANISH 2010-2012
 */

public class Main extends JFrame{
    private static class Controller extends JPanel{
    private ButtonGroup buttongroup, difficultyGroup;
    private static JRadioButton opt1, opt2, easy, medium, hard;    
    private static JLabel x,o,options, wins;
    private JButton resetButton,newButton;
    private JPanel[] pane = new JPanel[5];
    private static int xWins, oWins = 0;
    File file;
    Image xImage, oImage;
    ImageIcon xIcon, oIcon;
    Controller(){
        setPreferredSize(new Dimension(150, 500));
        setBackground(Color.lightGray);
        initComponents();
    }
    private static void xWins(){
        xWins++;
        x.setText(": " + xWins);
    }
    private static void oWins(){
        oWins++;
        o.setText(": " + oWins);
    }
    private void initComponents(){
        try{
            file = new File("media/xIcon.png");
            xImage = ImageIO.read(file);
            file = new File("media/oIcon.png");
            oImage = ImageIO.read(file); 
        }
        catch(IOException ie){
            JOptionPane.showMessageDialog(new JFrame(),
        		    "Media not found\n" + ie);
            System.exit(0);
        }
        
        xIcon = new ImageIcon(xImage);
        oIcon = new ImageIcon(oImage);
        resetButton = new JButton("Reset");
        resetButton.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e) {
            resetButtonActionPerformed();
            }
        });
        newButton = new JButton("New Game");
        newButton.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e) {
            newButtonActionPerformed();
            }
        });
        buttongroup = new ButtonGroup();
        difficultyGroup = new ButtonGroup();
        opt1 = new JRadioButton("2 Player");
        opt1.setBackground(Color.lightGray);
        opt1.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e) {
            opt1ActionPerformed();
            }
        });
        opt2 = new JRadioButton("1 Player");
        opt2.setBackground(Color.lightGray);
        opt2.addActionListener(new ActionListener(){
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
        for(int z = 0; z < 5; z++){
            pane[z] = new JPanel();
            pane[z].setBackground(Color.lightGray);
        }
        pane[0].setLayout(new BoxLayout(pane[0],BoxLayout.PAGE_AXIS));
        pane[0].add(Box.createRigidArea(new Dimension(0,10)));
        pane[0].add(wins);
        pane[0].add(Box.createRigidArea(new Dimension(0,4)));
        pane[0].add(x);
        pane[0].add(o);
        pane[0].add(Box.createRigidArea(new Dimension(0,6)));
        pane[0].add(resetButton);
        pane[0].add(Box.createRigidArea(new Dimension(0,20)));
        pane[1].setLayout(new BoxLayout(pane[1],BoxLayout.PAGE_AXIS));
        pane[1].add(Box.createRigidArea(new Dimension(0,100)));
        pane[1].add(options);
        pane[1].add(Box.createRigidArea(new Dimension(0,6)));
        pane[1].add(opt1);
        pane[1].add(opt2);
        pane[2].setLayout(new BoxLayout(pane[2],BoxLayout.PAGE_AXIS));
        pane[2].setBorder(BorderFactory.createEmptyBorder(0,15,0,0));
        pane[2].add(easy);
        pane[2].add(medium);
        pane[2].add(hard);
        pane[1].add(pane[2]);
        pane[2].setVisible(false);
        pane[1].add(Box.createRigidArea(new Dimension(0,6)));
        pane[1].add(newButton);
        add(pane[0]);
        add(pane[1]);
        
    }
    private void opt1ActionPerformed(){
        pane[2].setVisible(false);
    }
    private void opt2ActionPerformed(){
        pane[2].setVisible(true);
    }
    private void resetButtonActionPerformed(){
        xWins = 0;
        oWins = 0;
        x.setText(": " + xWins);
        o.setText(": " + oWins);
    }
    private void newButtonActionPerformed(){
        GameBoard.Clear();
        }
    public static int getGameType(){
        int x;
        if (opt1.isSelected()){
            x = 0;
        }
        else{
            x = 1;
        }
        return x;
    }
    public static int getDifficulty(){
        int x = -1;
        if (easy.isSelected()){
            x = 0;
        }
        if (medium.isSelected()){
            x = 1;
        }
        if (hard.isSelected()){
            x = 2;
        }
        return x;
    }
  
}
    
private static class GameBoard extends Canvas{

private static int win;
private int y = 40, x = 40;
private static int xWins,oWins = 0;
private static boolean Xturn = true;
private static int[][] winCombos = {{0,3,6},{0,1,2},{9,12,15},{9,10,11},
{18,21,24},{18,19,20},{1,4,7},{3,4,5},{10,13,16},{12,13,14},{19,22,25},
{21,22,23},{2,5,8},{6,7,8},{11,14,17},{15,16,17},{20,23,26},{24,25,26},{0,9,18},
{3,12,21},{1,10,19},{4,13,22},{2,11,20},{5,14,23},{0,4,8},{9,13,17},{18,22,26},
{24,22,20},{15,13,11},{6,4,2},{0,13,26},{2,13,24},
{6,13,20},{8,13,18},{0,10,20},{3,13,23},{6,16,26},{2,10,18},{0,12,24},{6,12,18},
{5,13,21},{1,13,25},{7,13,19},{8,16,24},{2,14,26},{8,14,20},{7,16,25},{6,15,24},{8,17,26}};
private static int[] cell = new int[27];
private static int[] winners = new int[3];
private static int[] cellRank = {7,4,7,4,5,4,7,4,7,4,5,4,5,13,5,4,5,4,7,4,7,4,5,4,7,4,7};
private static int ai1, ai2, ai3 = 0;
private boolean doOnce = true;
private static Random rdm = new Random();
private static boolean repaintPlease = false;
private Image mImage;
private File file;
private BufferedImage background, xImage, circleImage, turnImage, winImage, xIcon, oIcon, refresh, highlightImage;
javax.swing.Timer repaintTimer = new javax.swing.Timer(850, new ActionListener() {
          public void actionPerformed(ActionEvent e) {
              if(repaintPlease){
                    repaint();
                    repaintPlease = false;
              }
       
          }
       });
//***********************************************************
public static void Clear(){
    int[] values = {7,4,7,4,5,4,7,4,7,4,5,4,5,13,5,4,5,4,7,4,7,4,5,4,7,4,7};
    winners[0] = -1;
    winners[1]= -1;
    winners[2] = -1;
    ai1 = 0;
    ai2 = 0;
    ai3 = 0;
            for (int p = 0; p < 27; p++){
            cell[p] = 0;
            cellRank[p] = values[p];
            System.out.println(cellRank[p]);
}
            win = 0;
            if(Controller.getGameType() == 1 && !Xturn && win == 0){
               moveAI();
    }
            repaintPlease = true;
            }
//***********************************************************
private static void updateAI(){
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
private static void moveAI(){
    find3Best();
    double x = rdm.nextDouble();
    switch (Controller.getDifficulty()){
        case 0:
            //easy
            if(x > .95){ //5%
                //pick best move
                cell[ai3] = switchTurns();
            }
            else if(x > .5){
                //pick second best move
                cell[ai2] = switchTurns();
            }
            else{
                //pick third best move
                cell[ai3] = switchTurns();
            }
            break;
        case 1:
            //medium
            if(x > .5){
                //pick best move
                cell[ai3] = switchTurns();
            }
            else if(x > .05){
                //pick second best move
                cell[ai2] = switchTurns();
            }
            else{
                //pick third best move
                cell[ai1] = switchTurns();
            }
            break;
        case 2:
            //hard
            //pick best move
            cell[ai3] = switchTurns();
            break;
            
    }
    checkWin();
}
private static void find3Best(){
    updateAI();
    int a = 0;
    int b = 0;
    int c = 0;
    for(int y = 0; y < cellRank.length; y++){
        if (cellRank[y] > c){
            ai1 = ai2;
            ai2 = ai3;
            ai3 = y;
            a = b;
            b = c;
            c = cellRank[y];
        }
        else if (cellRank[y] > b){
            ai1 = ai2;
            ai2 =  y;
            a = b;
            b = cellRank[y];
        }
        else if (cellRank[y] > a){
            ai1 =  y;
            a = cellRank[y];
        }
    }
}
public static int switchTurns(){
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
//***********************************************************
private static int check(){
    //shortened over 80 lines
//supposedly checks for every given combination for every grid
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
//***********************************************************
public static void checkWin(){
    win = check();
    System.out.println("win = " + win);
    if(Controller.getGameType() == 1 && !Xturn && win == 0){
        moveAI();
    }
}
//***********************************************************
private class GameListener implements MouseListener {
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
        if(repaintPlease){
            repaint();
        }
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
        public GameBoard(){
        setPreferredSize(new Dimension(350, 500));
        setBackground(Color.lightGray);
        addMouseListener(new GameListener());
        Clear();

        }
        @Override
        	   public void paint (Graphics g)
	   	{	 //refreshCount++;
	   		 Dimension d = getSize();
	   		 checkOffscreenImage();
	   		 Graphics offG = mImage.getGraphics();
	   		 offG.setColor(getBackground());
	   		 offG.fillRect(0, 0, d.width, d.height);
	   		 paintOffscreen(mImage.getGraphics());
	      	 g.drawImage(mImage, 0, 0, null);
	   	}
	   	private void checkOffscreenImage() {
	   	    Dimension d = getSize();
	   	    if (mImage == null || mImage.getWidth(null) != d.width
	   	        || mImage.getHeight(null) != d.height) {
	   	      mImage = createImage(d.width, d.height);
	   	    }
	   	  }

	   public void paintOffscreen(Graphics g) {
		if(doOnce){
                   initComponents();
                   doOnce = false;
            }
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
    if (win == 1){
        g.drawImage(xIcon, 280,10,null);
        xWins++;
        Controller.xWins();
        System.out.println("x: " + xWins + " o: " + oWins);
    }
    else{
        g.drawImage(oIcon, 280,10,null);
        oWins++;
        Controller.oWins();
        System.out.println("x: " + xWins + " o: " + oWins);
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
    public static void main(String[] args) {
    new Main();
    

    }
    Main(){
        setTitle("Tic Tac Toe 3D Perspective");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Controller control = new Controller();
        GameBoard board = new GameBoard();
        JPanel mainpanel = new JPanel();
        mainpanel.setBackground(Color.lightGray);
        mainpanel.setMinimumSize(new Dimension(500,500));
        mainpanel.setMaximumSize(new Dimension(500,500));
        mainpanel.add(board);
        mainpanel.add(control);
        setResizable(false);
        add(mainpanel);
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }
}


