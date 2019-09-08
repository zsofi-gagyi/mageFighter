package game.characters.Managers;

import game.characters.Hero;
import game.characters.Mage;
import game.characters.Robot;
import game.characters.abstractCategories.Enemy;
import game.specialScreens.Battle;
import game.Game;

import java.awt.*;
import java.util.ArrayList;

import static game.characters.abstractCategories.Enemy.drawEnemy;

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
        currentGame.hero.isFighting = true;
        currentGame.enemyManager.currentlyFighting = enemy;
        currentGame.battle = new Battle(true);
      }
    }
  }

  public void moveMonsters(Game game, Hero hero, EnemyManager enemyManager, Battle battle, char[][] floorPlanMatrix) {
    for (Enemy enemy : this.monsterList) {
      if (enemy.healthPoint > 0) {
        enemy.moveIfSpeedAllows(game, hero, enemyManager, battle, floorPlanMatrix);
      }
    }
  }

  public void drawMonsters(Graphics graphics) {
    for (Enemy enemy : this.monsterList) {
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

