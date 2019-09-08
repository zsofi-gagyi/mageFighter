package game.characters;

import game.characters.abstractCategories.Character;
import game.Levels.Level;
import game.Game;
import inputOutput.PositionedImage;

import java.awt.*;

public class Hero extends Character {
  public boolean isFighting;
  public int diceResult = 0;

  public Hero() {
    super.name = "Hero";
    super.appearance = "img/hero-down.png";
    super.healthPoint = 36;
    super.defendPoint = 12;
    super.strikePoint = 12;
    isFighting = false;

    super.maxHealth = super.healthPoint;
    this.xCoordinate = 0;
    this.yCoordinate = 0;
  }

  public static void drawHero(Hero hero, Graphics graphics) {
    PositionedImage heroDrawn = new PositionedImage(hero.appearance,
      hero.xCoordinate * 72, hero.yCoordinate * 72);
    heroDrawn.draw(graphics);
  }

  public void attack(Game currentGame) {
    fight(this, currentGame.enemyManager.currentlyFighting);

    currentGame.battle.isHerosTurn = false;

    if (currentGame.enemyManager.currentlyFighting.healthPoint <= 0) {
      currentGame.battle.isNotOver = false;
      currentGame.hero.getPointsAfterBattle();

      if (currentGame.enemyManager.allRelevantDead()){
        currentGame.isShowingStatsBetweenLevels = true;
      }
    }
  }

  public void getAttacked(Game currentGame) {
    fight(currentGame.enemyManager.currentlyFighting, this);

    currentGame.battle.isHerosTurn = true;

    if (this.healthPoint <= 0) {
      currentGame.isOver = true;
    }
  }

  private void fight(Character attacker, Character defendant) {
    this.diceResult = d6();

    int strikeValue = attacker.strikePoint + this.diceResult;

    if (strikeValue > defendant.defendPoint) {
      defendant.healthPoint -=  Math.abs (attacker.strikePoint - defendant.defendPoint);
    }
  }

  private void getPointsAfterBattle() {
    this.healthPoint += d6();
    this.defendPoint += d6();
    this.strikePoint += d6();

    this.maxHealth = Math.max(this.healthPoint, this.maxHealth);
  }

  public void getPointsAfterLevel() {
    int healthToBeRegained = this.maxHealth - this.healthPoint;
    if (healthToBeRegained > 0) {
      if (Math.random() > 0.5) {
        this.healthPoint += healthToBeRegained / 10;
      }
      if (Math.random() > 0.6) {
        this.healthPoint += healthToBeRegained * 7 / 30;
      }
      if (Math.random() > 0.9) {
        this.healthPoint = this.maxHealth;
      }
    }
  }

}
