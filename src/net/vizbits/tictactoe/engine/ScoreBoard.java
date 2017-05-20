package net.vizbits.tictactoe.engine;

import java.util.HashMap;
import java.util.Map;

public class ScoreBoard {

  private Map<Player, Integer> scoreMap = new HashMap<Player, Integer>();

  public ScoreBoard() {
    resetScores();
  }

  public void resetScores() {
    for (Player player : Player.values()) {
      scoreMap.put(player, 0);
    }
  }
  
  public void addPoint(Player player) {
    int previousScore = scoreMap.get(player);
    scoreMap.put(player, previousScore + 1);
  }

  public int getPoints(Player player) {
    return scoreMap.get(player);
  }

}
