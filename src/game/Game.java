package game;

import game.characters.Hero;
import game.characters.MushroomWithBox;
import game.characters.Managers.EnemyManager;
import game.Levels.Level;
import game.specialScreens.Battle;
import game.specialScreens.BetweenLevels;
import game.specialScreens.Stats;

import java.awt.*;

public class Game {
  public int levelNumber = -1;
  public Hero hero;
  public Level level;
  public MushroomWithBox mushroomWithBox;
  public EnemyManager enemyManager;
  public Battle battle;
  public boolean isOver = false;
  public boolean isShowingStatsBetweenLevels = true;

  public Game() {
  }

  public void drawEverything(Graphics graphics) {
    if (!this.isOver) {
      if (!isShowingStatsBetweenLevels) {

        if (!this.hero.isFighting) {

          Level.drawLevel(this.level, graphics);
          MushroomWithBox.drawMushroom(this.mushroomWithBox, graphics);
          this.enemyManager.drawMonsters(graphics);
          Hero.drawHero(this.hero, graphics);

        } else {
          this.battle.drawBattle(this.hero, this.enemyManager.currentlyFighting, graphics);
        }

        Stats.drawStats(this.levelNumber, this.hero, this.enemyManager.currentlyFighting, graphics);
      } else {
        BetweenLevels.drawBetweenLevels(this.levelNumber, graphics);
      }
    } else {
      this.drawGameOver(graphics);
    }
  }

  public void arrangeLevel(){
    this.levelNumber++;
    this.hero = new Hero();
    this.level = new Level(levelNumber);
    this.mushroomWithBox = new MushroomWithBox(this.level.floorPlanMatrix);
    this.enemyManager = new EnemyManager(this.levelNumber, this.hero, this.level.floorPlanMatrix);
    this.hero.getPointsAfterLevel();
    this.hero.yCoordinate = 0;
    this.hero.xCoordinate = 0;
  }

  public void drawGameOver(Graphics graphics) {
    graphics.setColor(Color.BLACK);
    graphics.fillRect(0, 0, 720, 850);

    graphics.setColor(Color.WHITE);
    graphics.setFont(new Font(Font.MONOSPACED, Font.BOLD, 65));
    graphics.drawString(" Game over :(", 100, 425);
  }
}
