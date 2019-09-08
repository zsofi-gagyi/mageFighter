package game.Levels;

import game.characters.MushroomWithBox;
import game.characters.Managers.EnemyManager;
import game.Levels.helperClasses.RandomFloorPlan;
import game.Game;
import inputOutput.PositionedImage;

import java.awt.*;

public class Level {
  public String[][] tiles;
  public String currentFloor;
  public String currentWall;
  public char[][] floorPlanMatrix;


  public Level(int levelNumber) {
    this.currentFloor = tilePicker(false);
    this.currentWall = tilePicker(true);

    switch (levelNumber) {
      case 0:
        this.floorPlanMatrix = new char[][]{
          {' ', ' ', ' ', ' ', ' ', 'O', ' ', ' ', ' ', ' '},

          {' ', 'O', ' ', ' ', ' ', 'O', ' ', ' ', 'O', ' '},

          {' ', 'O', 'O', 'O', ' ', 'O', ' ', 'O', 'O', ' '},

          {' ', ' ', ' ', 'O', ' ', 'O', ' ', 'O', ' ', ' '},

          {'O', 'O', 'O', 'O', ' ', 'O', ' ', 'O', 'O', 'O'},

          {' ', ' ', ' ', ' ', ' ', 'O', ' ', ' ', ' ', ' '},

          {' ', 'O', 'O', 'O', ' ', 'O', 'O', 'O', 'O', ' '},

          {' ', 'O', ' ', 'O', ' ', 'O', ' ', ' ', ' ', ' '},

          {' ', 'O', ' ', 'O', ' ', 'O', ' ', 'O', ' ', ' '},

          {' ', ' ', ' ', 'O', ' ', ' ', ' ', 'O', ' ', ' '},
        };
        break;

        //here could come some more non-random levels...

      default:
        RandomFloorPlan thisPlan = new RandomFloorPlan(15 + (int) (Math.random() * 20));
        this.floorPlanMatrix = thisPlan.matrix;
    }

    generateFloor(this.floorPlanMatrix);


  }

  public static void drawLevel(Level level, Graphics graphics) {
    for (int i = 0; i < 10; i++) {
      for (int j = 0; j < 10; j++) {
        PositionedImage image = new PositionedImage(level.tiles[i][j], i * 72, j * 72);
        image.draw(graphics);
      }
    }
  }

  private String tilePicker(boolean isWall) {
    String tile;

    if (isWall) {
     tile = "img/wall" + (int)(Math.random() * 8) + ".png";
    } else {
     tile = "img/floor" + (int)(Math.random() * 8) + ".png";
    }

    return tile;
  }

  private void generateFloor(char[][] floorPlanMatrix) {
    this.tiles = new String[10][10];

    for (int i = 0; i < 10; i++) {
      for (int j = 0; j < 10; j++) {
        if (floorPlanMatrix[i][j] == 'O') {
          this.tiles[i][j] = this.currentWall;
        } else {
          this.tiles[i][j] = this.currentFloor;
        }
      }
    }
  }
}
