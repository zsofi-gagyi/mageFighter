package gameLogic.specialScreens;

import java.awt.*;

public class BetweenLevels {

  public static void drawBetweenLevels(int level, Graphics graphics) {
    graphics.setColor(Color.BLACK);
    graphics.fillRect(0, 0, 720, 850);

    graphics.setColor(Color.WHITE);
    graphics.setFont(new Font(Font.MONOSPACED, Font.BOLD, 45));

    if (level == -1) {
      drawBeforeFirst(graphics);
    } else {
      drawAfterLevels(level, graphics);
    }
  }

  private static void drawBeforeFirst(Graphics graphics){
    graphics.drawString("Welcome to mageFighter! ", 50, 325);

    graphics.setFont(new Font(Font.MONOSPACED, Font.BOLD, 25));
    graphics.drawString("To finish a level, you will need to", 100, 450);
    graphics.drawString("kill the mage, and the robot which", 100, 490);
    graphics.drawString("           has the key.", 100, 530);

    graphics.setFont(new Font(Font.MONOSPACED, Font.BOLD, 35));
    graphics.drawString("Press 'space' to continue", 100, 600);
  }

  private static void drawAfterLevels(int level, Graphics graphics){
    graphics.drawString(" Finished level " + (level + 1), 100, 425);
    graphics.drawString("      Bravo!     ", 100, 470);

    graphics.setFont(new Font(Font.MONOSPACED, Font.BOLD, 35));
    graphics.drawString("Press 'space' to continue", 100, 600);
  }
}
