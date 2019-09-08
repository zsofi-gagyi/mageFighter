package gameLogic;

import gameLogic.characters.Hero;
import gameLogic.characters.MushroomWithBox;
import gameLogic.characters.managers.EnemyManager;
import gameLogic.levels.Level;
import gameLogic.specialScreens.Battle;
import gameLogic.specialScreens.BetweenLevels;
import gameLogic.specialScreens.Stats;

import java.awt.*;

public class Game {
  public enum GameState {textScreen, board, battle, over}

  public GameState gameState;
  private int levelNumber;
  public Hero hero;
  public Level level;
  public MushroomWithBox mushroomWithBox;
  public EnemyManager enemyManager;
  public Battle battle;

  public Game() {
    this.levelNumber = -1;
    this.gameState = GameState.textScreen;
  }

  public void drawEverything(Graphics graphics) {

    switch (gameState){

      case board:
        Level.drawLevel(this.level, graphics);
        MushroomWithBox.drawMushroom(this.mushroomWithBox, graphics);
        EnemyManager.drawMonsters(this.enemyManager.monsterList, graphics);
        Hero.drawHero(this.hero, graphics);
        Stats.drawStats(this.gameState, this.levelNumber, this.hero, this.enemyManager.currentlyFighting, graphics);
        break;

      case battle:
        Battle.drawBattle(this.battle, this.hero, this.enemyManager.currentlyFighting, graphics);
        Stats.drawStats(this.gameState, this.levelNumber, this.hero, this.enemyManager.currentlyFighting, graphics);
        break;

      case textScreen:
        BetweenLevels.drawBetweenLevels(this.levelNumber, graphics);
        break;

      case over:
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

  private void drawGameOver(Graphics graphics) {
    graphics.setColor(Color.BLACK);
    graphics.fillRect(0, 0, 720, 850);

    graphics.setColor(Color.WHITE);
    graphics.setFont(new Font(Font.MONOSPACED, Font.BOLD, 65));
    graphics.drawString(" Game over :(", 100, 425);
  }
}
