package net.vizbits.tictactoe;

import java.util.Arrays;
import java.util.Random;

public class AI {
	private int[] cellRank = {7,4,7,4,5,4,7,4,7,4,5,4,5,13,5,4,5,4,7,4,7,4,5,4,7,4,7}; // auto? count each number
	public static int[] values = {7,4,7,4,5,4,7,4,7,4,5,4,5,13,5,4,5,4,7,4,7,4,5,4,7,4,7};
	private int ai[] = {0,0,0};
	private GameBoard game;
	private Controller controller;
	private Random random = new Random();
	public AI(GameBoard game, Controller controller){
		this.game = game;
		this.controller = controller;
	}
	public void reset(){
		for (int p = 0; p < 27; p++){
	        cellRank[p] = values[p];
	    }
		Arrays.fill(ai, 0);
	}
	//***********************************************************
		private void update(){
		    //set ranks
		    for(int i = 0; i < GameBoard.winCombos.length; i++){
		        int a = GameBoard.winCombos[i][0];
		        int b = GameBoard.winCombos[i][1];
		        int c = GameBoard.winCombos[i][2];
		        if(game.cells[a].value == game.cells[b].value && game.cells[a].value != 0){
		            cellRank[c] += 15;
		        }
		        if(game.cells[a].value == game.cells[c].value && game.cells[a].value != 0){
		            cellRank[b] += 15;
		        }
		        if(game.cells[c].value == game.cells[b].value && game.cells[c].value != 0){
		            cellRank[a] += 15;
		        }
		    }
		    for(int i = 0; i < game.cells.length; i++){
		        if(game.cells[i].value != 0){
		            cellRank[i] = 0;
		        }
		     }
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
		public int getMove(){
		    update();
		    double x = random.nextDouble();
		    switch (controller.getDifficulty()){
		        case 0:
		            //easy
		            if(x > .90){ //10%
		                //pick best move
		            	return ai[2];
		            }
		            else if(x > .3){ //60%
		                //pick second best move
		            	return ai[1];
		            }
		            else{ //30%
		                //pick third best move
		               return ai[0];
		            }
		        case 1:
		            //medium
		            if(x > .5){
		                //pick best move
		            	return ai[2];
		            }
		            else{
		                //pick second best move
		            	return ai[1];
		            }
		        default: //hard
		        	return ai[2];		            
		    }
		}
}
