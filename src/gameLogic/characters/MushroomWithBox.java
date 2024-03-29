package gameLogic.characters;

import inputOutput.ImageDrawer;

import java.awt.*;

public class MushroomWithBox {
  public int xCoordinate;
  public int yCoordinate;
  public String status = "initial";
  private String box = "/questionMark.png";
  private String mushroom = "/mushroom.png";

  public MushroomWithBox(char[][] floorPlanMatrix) {
    boolean isOnWall = false;
    boolean hasFloorAbove= false;
    boolean hasFloorBelow= false;

    do {
      this.xCoordinate = (int) (Math.random() * 10);
      this.yCoordinate = (int) (Math.random() * 8) + 1;

      isOnWall =      (floorPlanMatrix[xCoordinate][yCoordinate] == 'O');
      hasFloorAbove = (floorPlanMatrix[xCoordinate][yCoordinate - 1] == ' ');
      hasFloorBelow = (floorPlanMatrix[xCoordinate][yCoordinate + 1] == ' ');

    } while (!isOnWall || !hasFloorAbove || !hasFloorBelow);
  }

  public static void interactWithMushroom(MushroomWithBox mushroomWithBox, Hero hero, String direction) {
    switch (mushroomWithBox.status) {
      case "initial":
        boolean isMushroomAboveHero = mushroomWithBox.mushroomIsAbove(hero);
        if (isMushroomAboveHero && direction.equals("up")) {
          mushroomWithBox.status = "activated";
        }
        break;
      case "activated":
        eatMushroomIfRelevant(hero, mushroomWithBox);
    }
  }

  private static void eatMushroomIfRelevant(Hero hero, MushroomWithBox mushroomWithBox) {

      if (hero.xCoordinate == mushroomWithBox.xCoordinate &&
          hero.yCoordinate == mushroomWithBox.yCoordinate - 1) {

        hero.healthPoint += 10;
        hero.maxHealth = Math.max(hero.maxHealth, hero.healthPoint);
        mushroomWithBox.status = "spent";
      }
  }

  public static void drawMushroom(MushroomWithBox mushroomWithBox, Graphics graphics) {
    ImageDrawer mushroomBoxDraw = new ImageDrawer(mushroomWithBox.box,
                                                     mushroomWithBox.xCoordinate * 72,
                                                     mushroomWithBox.yCoordinate * 72);
    mushroomBoxDraw.draw(graphics);

    if (mushroomWithBox.status.equals("activated")) {
        ImageDrawer mushroomDrawn = new ImageDrawer(mushroomWithBox.mushroom,
                                                       mushroomWithBox.xCoordinate * 72,
                                                      (mushroomWithBox.yCoordinate - 1) * 72);
        mushroomDrawn.draw(graphics);
      }
    }


  private boolean mushroomIsAbove(Hero hero) {
    return (this.xCoordinate == hero.xCoordinate) && (this.yCoordinate == (hero.yCoordinate - 1));
  }
}
