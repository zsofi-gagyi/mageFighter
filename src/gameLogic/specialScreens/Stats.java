package gameLogic.specialScreens;

import gameLogic.Game;
import gameLogic.characters.Hero;
import gameLogic.characters.abstractCategories.Character;
import gameLogic.characters.abstractCategories.Enemy;

import java.awt.*;

public class Stats {

  public static void drawStats(Game.GameState state, int level, Hero hero, Enemy enemy, Graphics graphics) {
    graphics.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 21));

    drawHeroStats(level, hero, graphics);

    if (state.equals(Game.GameState.battle)) {
      drawEnemyStats(enemy, graphics);
    }
  }

  public static void drawHeroStats(int level, Hero hero, Graphics graphics) {
    graphics.setColor(Color.WHITE);
    graphics.fillRect(0, 720, 720, 120);

    graphics.setColor(Color.BLACK);

    String heroStats = getStatsFor(hero, level);
    graphics.drawString(heroStats, 10, 750);
  }

  public static void drawEnemyStats(Enemy enemy, Graphics graphics) {
      graphics.setColor(enemy.statsColor);
      graphics.fillRect(0, 760, 720, 60);

      graphics.setColor(Color.BLACK);

      String enemyStats = getStatsFor(enemy, 0);
      graphics.drawString(enemyStats, 10, 800);
  }

  private static String getStatsFor(Character character, int level){
    return character.name +
      " | Level " + (character instanceof Enemy ? ((Enemy) character).enemyLevel + 1 : level + 1) +
      " | Health: " +  character.healthPoint + " (max: " + character.maxHealth +
      ") | Defense: " + character.defendPoint +
      " | Strike: " + character.strikePoint;
  }
}
