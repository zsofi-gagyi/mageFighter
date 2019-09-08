package gameLogic.characters.managers;

import gameLogic.characters.Hero;
import gameLogic.characters.Mage;
import gameLogic.characters.Robot;
import gameLogic.characters.abstractCategories.Enemy;
import gameLogic.specialScreens.Battle;
import gameLogic.Game;

import java.awt.*;
import java.util.ArrayList;

import static gameLogic.characters.abstractCategories.Enemy.drawEnemy;

public class EnemyManager {
  public ArrayList<Enemy> monsterList;
  public Enemy currentlyFighting;

  public EnemyManager(int level, Hero hero, char[][] floorPlanMatrix) {
    this.monsterList = new ArrayList<>();

    Enemy robot1 = new Robot(level, hero, this.monsterList, floorPlanMatrix, "Robot keyholder", true);
    this.monsterList.add(robot1);

    for (int i = 0; i < Math.min(((int) (level * Math.random()) + 2), 4); i++) {
      Enemy robot2 = new Robot(level, hero, this.monsterList, floorPlanMatrix, "Robot", false);
      this.monsterList.add(robot2);
      if (i > 3) {
        i = level + 2; //TODO wtf is happening here?
      }
    }

    Enemy mage = new Mage(level, hero, monsterList, floorPlanMatrix, "Mage");
    this.monsterList.add(mage);
  }

  public static void checkMonsters(Game currentGame) {
    for (Enemy enemy : currentGame.enemyManager.monsterList) {
      boolean heroSteppedOnMonster = ((enemy.xCoordinate == currentGame.hero.xCoordinate) &&
                                      (enemy.yCoordinate == currentGame.hero.yCoordinate));

      if (heroSteppedOnMonster && (enemy.healthPoint > 0)) {
        currentGame.gameState = Game.GameState.battle;
        currentGame.enemyManager.currentlyFighting = enemy;
        currentGame.battle = new Battle(true);
      }
    }
  }

  public void moveMonsters(Game game) {
    for (Enemy enemy : this.monsterList) {
      if (enemy.healthPoint > 0) {
        enemy.moveIfSpeedAllows(game);
      }
    }
  }

  public static void drawMonsters(ArrayList<Enemy> enemies, Graphics graphics) {
    for (Enemy enemy : enemies) {
      if (enemy.healthPoint > 0) {
        drawEnemy(enemy, graphics);
      }
    }
  }

  public boolean allRelevantDead() {
    boolean mageIsDead = false;
    boolean keyholderIsDead = false;

    for (Enemy enemy : this.monsterList) {
      if (enemy instanceof Mage) {
        if (enemy.healthPoint <= 0) {
          mageIsDead = true;
        }
      } else if (((Robot)enemy).isKeyholder && enemy.healthPoint <= 0) {
        keyholderIsDead = true;
      }
    }

    return (mageIsDead && keyholderIsDead);
  }
}

