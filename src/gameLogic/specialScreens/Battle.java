package gameLogic.specialScreens;

import gameLogic.characters.Hero;
import gameLogic.characters.abstractCategories.Enemy;
import inputOutput.ImageDrawer;

import java.awt.*;

public class Battle {
  public enum BattleState {willBeginWithHero, willBeginWithEnemy, herosTurn, enemysTurn, over}

  public BattleState battleState;

  public Battle(boolean isHerosTurn) { //TODO store battle participants in fields
    if (isHerosTurn){
      this.battleState = BattleState.willBeginWithHero;
    } else {
      this.battleState = BattleState.willBeginWithEnemy;
    }
  }

  public static void drawBattle(Battle battle, Hero hero, Enemy enemy, Graphics graphics) {
    String battleStatus;
    String secondBattleStatus;

    switch (battle.battleState){
      case willBeginWithHero:
      case willBeginWithEnemy:
        drawFirstImage(enemy, graphics);
        battleStatus =       "       A battle begins! Press 'space'      ";
        secondBattleStatus = "      to advance and to roll the dice!";
        break;

      case herosTurn:
        drawHeroAttacking(enemy, graphics);
        battleStatus = "                      The enemy rolled a " + hero.diceResult + "!"; // TODO refactor this dice roll out from "hero"
        secondBattleStatus = "You attack!";
        break;

      case enemysTurn:
        drawEnemyAttacking(enemy, graphics);
        battleStatus = "You rolled a " + hero.diceResult + "!";
        secondBattleStatus = "                         The enemy attacks!";
        break;

      default:
        drawLastImage(graphics);
        battleStatus = "                  You won!                    ";
        secondBattleStatus = "";
    }

    graphics.setFont(new Font(Font.MONOSPACED, Font.BOLD, 26));
    graphics.setColor(Color.WHITE);
    graphics.drawString(battleStatus, 10, 650);
    graphics.drawString(secondBattleStatus, 10, 680);
  }

  private static void drawFirstImage(Enemy enemy, Graphics graphics) {
    drawGreyBackground(graphics);

    drawImage(graphics, "img/battleHero.png");

    String enemyImage = "img/battle" + trim(enemy.appearance) + ".png";
    drawImage(graphics, enemyImage);
  }

  private static void drawHeroAttacking(Enemy enemy, Graphics graphics) {
    drawBlackBackground(graphics);

    String enemyImage = "img/battle" + trim(enemy.appearance) + ".png";
    drawImage(graphics, enemyImage);

    drawImage(graphics, "img/battleHeroAttacking.png");
  }

  private static void drawEnemyAttacking(Enemy enemy, Graphics graphics) {
    drawBlackBackground(graphics);

    drawImage(graphics, "img/battleHero.png");

    String enemyImage = "img/battle" + trim(enemy.appearance) + "Attacking.png";
    drawImage(graphics, enemyImage);
  }

  private static void drawLastImage(Graphics graphics) {
    drawGreyBackground(graphics);
    drawImage(graphics, "img/battleHeroLastImage.png");
  }

  private static void drawImage(Graphics graphics, String image) {
    ImageDrawer battleHeroAttacking = new ImageDrawer(image, 0, 0);
    battleHeroAttacking.draw(graphics);
  }

  private static String trim(String input){
    input = input.replace("img/", "");
    return input.replace(".png", "");
  }

  private static void drawBlackBackground(Graphics graphics){
    graphics.setColor( Color.BLACK);
    graphics.fillRect(0,0, 720, 720);
  }

  private static void drawGreyBackground(Graphics graphics){
    graphics.setColor( new Color(112, 112, 112));
    graphics.fillRect(0,0, 720, 720);
  }
}


