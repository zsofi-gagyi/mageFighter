package game.characters;

import game.characters.abstractCategories.Enemy;
import game.Game;

import java.awt.*;
import java.util.ArrayList;

public class Mage extends Enemy {

  public Mage(int level, Hero hero, ArrayList<Enemy> monsterList, char[][] floorPlanMatrix, String name) {
    super(level, hero, monsterList, floorPlanMatrix, name);
    super.appearance = "img/mage.png";
    super.statsColor = new Color(200, 200, 200);

    super.healthPoint = (super.enemyLevel + 2) * 3;
    super.defendPoint = (super.enemyLevel + 2) * d6() / 3;
    super.strikePoint = (super.enemyLevel ) * d6();
    super.maxHealth = super.healthPoint;

    super.chanceOfChangingDirections = 0.4;
  }
}
