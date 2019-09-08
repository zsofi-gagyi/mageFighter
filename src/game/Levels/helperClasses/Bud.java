package game.Levels.helperClasses;

import generalHelper.Direction;

public class Bud {
  int xCoordinate;
  int yCoordinate;
  Direction direction;

  public Bud(Direction direction) {
    this.direction = direction;

    switch (direction.name) {
      case "up":
        this.xCoordinate = almostAnyNumber();
        this.yCoordinate = eightOrNine();
        break;
      case "down":
        this.xCoordinate = almostAnyNumber();
        this.yCoordinate = zeroOrOne();
        break;
      case "left":
        this.xCoordinate = eightOrNine();
        this.yCoordinate = almostAnyNumber();
        break;
      default:
        this.xCoordinate = zeroOrOne();
        this.yCoordinate = almostAnyNumber();
    }
  }

  private int almostAnyNumber() {
    return (int) ((Math.random() * 8) + 1);
  }

  private  int eightOrNine() {
    int result = 8;
    if (Math.random() < 0.5) {
      result = 9;
    }
    return result;
  }

  private int zeroOrOne() {
    int result = 0;
    if (Math.random() < 0.5) {
      result = 1;
    }
    return result;
  }
}
