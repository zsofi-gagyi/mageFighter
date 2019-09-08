package game.specialScreens;

import game.characters.Hero;
import game.characters.abstractCategories.Enemy;
import inputOutput.PositionedImage;

import java.awt.*;

public class Battle {
  public boolean hasJustBegan;
  public boolean isHerosTurn;
  public boolean isNotOver;

  public Battle(boolean isHerosTurn) {
    this.hasJustBegan = true;
    this.isHerosTurn = isHerosTurn;
    this.isNotOver = true;
  }

  public void drawBattle(Hero hero, Enemy enemy, Graphics graphics) {
    String battleStatus;
    String secondBattleStatus = "";

    if (hasJustBegan) {
      drawFirstImage(enemy, graphics);
      battleStatus =       "       A battle begins! Press 'space'      ";
      secondBattleStatus = "      to advance and to roll the dice!";
    } else {
      if (this.isNotOver) {
        if (isHerosTurn) {
          drawHeroAttacking(enemy, graphics);
          battleStatus = "                      The enemy rolled a " + hero.diceResult + "!"; // TODO refactor this out from "hero"
          secondBattleStatus = "You attack!";

        } else {
          drawEnemyAttacking(enemy, graphics);
          battleStatus = "You rolled a " + hero.diceResult + "!";
          secondBattleStatus = "                         The enemy attacks!";

        }
      } else {
        drawLastImage(graphics);
        battleStatus = "                  You won!                    ";

        hero.isFighting = false;
      }
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
    PositionedImage battleHeroAttacking = new PositionedImage(image, 0, 0);
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


