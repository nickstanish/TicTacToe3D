package net.vizbits.tictactoe;

/**
 *
 * @author Nick Stanish
 */
public class State {
    

    private GameType type;
    private GameDifficulty difficulty;
    private GameTurn turn;
    

    public State(){
        this(null,null,null);
    }
    
    /**
     * Creates new instance of state with default options if null
     * 
     * @param type
     * @param difficulty
     * @param turn 
     */
    public State(GameType type, GameDifficulty difficulty, GameTurn turn){
        this.type = (type != null ) ? type : GameType.SinglePlayer;
        this.difficulty = (difficulty != null ) ? difficulty : GameDifficulty.Medium;
        this.turn = (turn != null ) ? turn : GameTurn.None;
    }
    
    /**
     * Switch turn of current state
     * @return turn after switch
     */
    public GameTurn switchTurns(){
        if(turn == GameTurn.None)   turn = GameTurn.X;
        else    turn = (turn == GameTurn.X) ? GameTurn.O : GameTurn.X;
        
        return turn;
       
    }
    

    public void setTurn(GameTurn turn){
        this.turn = turn;
    }
    public GameTurn getTurn(){
        return this.turn;
    }
    
    public void setDifficulty(GameDifficulty difficulty){
        this.difficulty = difficulty;
    }
    public GameDifficulty getDifficulty(){
        return this.difficulty;
    }
    
    public void setType(GameType type){
        this.type = type;
    }
    public GameType getType(){
        return this.type;
    }
}
