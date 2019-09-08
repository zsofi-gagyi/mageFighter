package gameLogic.characters;

import gameLogic.characters.abstractCategories.Enemy;

import java.awt.*;
import java.util.ArrayList;

public class Robot extends Enemy {
  public boolean isKeyholder;

  public Robot(int level, Hero hero, ArrayList<Enemy> monsterList, char[][] floorPlanMatrix,
                                                                          String name, boolean isKeyholder) {
    super(level, hero, monsterList, floorPlanMatrix, name);
    super.appearance = "img/robot.png";
    super.statsColor = new Color(225, 40, 52);
    if (Math.random() > 0.5) {
      super.appearance = "img/robot2.png";
      super.statsColor = new Color(88, 82, 141);
    }

    this.isKeyholder = isKeyholder;
    super.chanceOfChangingDirections = 0.1;

    super.healthPoint = (super.enemyLevel + 1) * 3 + d6();
    super.defendPoint = (super.enemyLevel + 2) + d6() / 3;
    super.strikePoint = (super.enemyLevel + 1) + d6() / 2;

    super.maxHealth = super.healthPoint;
  }
}
