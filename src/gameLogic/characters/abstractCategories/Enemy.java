package gameLogic.characters.abstractCategories;

import gameLogic.characters.Hero;
import generalHelper.Direction;
import gameLogic.specialScreens.Battle;
import gameLogic.Game;
import inputOutput.ImageDrawer;

import java.awt.*;
import java.util.ArrayList;

public class Enemy extends Character {
  private int slowness = 1;
  private Direction direction;
  public int enemyLevel;
  public Color statsColor;
  private int untilItsTurnToMove = 0;
  public double chanceOfChangingDirections; // this refers to spontaneous direction changes,
                                            // as opposed to the necessary ones.

  public Enemy(int level, Hero hero, ArrayList<Enemy> monsterList, char[][] floorPlanMatrix, String name) {
    this.enemyLevel = decideLevel(level);
    super.name = name;

    if (level < 3) {
      this.slowness += (3 - this.enemyLevel);
    }

    Direction[] directionS = Direction.values();
    this.direction = directionS[(int) (Math.random() * 4)];

    boolean isNotOnWall, isNotOnHero, isNotOnOtherMonsters;

    do {
      this.xCoordinate = (int) (Math.random() * 10);
      this.yCoordinate = (int) (Math.random() * 10);

      isNotOnWall = (floorPlanMatrix[super.xCoordinate][super.yCoordinate] == ' ');
      isNotOnHero = !this.steppedOnHero(hero);
      isNotOnOtherMonsters = !alreadyOccupiedByMonsters(monsterList, super.xCoordinate, super.yCoordinate);

    } while (!(isNotOnWall && isNotOnHero && isNotOnOtherMonsters));
  }

  private static boolean alreadyOccupiedByMonsters(ArrayList<Enemy> monsterList, int xCoordinate, int yCoordinate) {
    boolean isOccupiedByOtherMonsters = false;

      for (Enemy enemy : monsterList) {
        if (enemy.xCoordinate == xCoordinate &&
            enemy.yCoordinate == yCoordinate) {
          isOccupiedByOtherMonsters = true;
        }
      }

    return isOccupiedByOtherMonsters;
  }

  private static int decideLevel(int gameLevel) {
    int enemyLevel = gameLevel + 1;
    if (Math.random() > 0.7) {
      enemyLevel++;
    }

    return enemyLevel;
  }

  public void moveIfSpeedAllows(Game game) {

    if (this.slowness < 2) {
      this.move(game);

    } else if (this.untilItsTurnToMove == 0) {
      this.move(game);
      this.untilItsTurnToMove += this.slowness - 1;

    } else {
      this.untilItsTurnToMove--;

    }
  }

  public static void drawEnemy(Enemy currentEnemy, Graphics graphics) {
    ImageDrawer enemyDraw = new ImageDrawer(currentEnemy.appearance,
      currentEnemy.xCoordinate * 72, currentEnemy.yCoordinate * 72);

    enemyDraw.draw(graphics);
  }

  private void move(Game game) {
    boolean canMove = true;

    if (Math.random() < this.chanceOfChangingDirections) {

      Direction[] directionS = Direction.values();
      this.direction = directionS[(int) (Math.random() * 4)];
    }

    if (!(this.canMoveInThisDirection(game.enemyManager.monsterList, game.level.floorPlanMatrix, this.direction))) {
      ArrayList<Direction> possibleDirections = this.checkDirections(game.enemyManager.monsterList,
                                                                     game.level.floorPlanMatrix);

      if (possibleDirections.isEmpty()) {
        canMove = false;
      } else {
        this.direction = possibleDirections.get((int) (Math.random() * possibleDirections.size()));
      }
    }

    if (canMove) {
      this.makeAStep(this.direction);
      if (this.steppedOnHero(game.hero)) {
        game.enemyManager.currentlyFighting = this;
        game.battle = new Battle(false);
        game.gameState = Game.GameState.battle;
      }
    }
  }

  private boolean steppedOnHero(Hero hero) {
    return (this.xCoordinate == hero.xCoordinate) && (this.yCoordinate == hero.yCoordinate);
  }

  private ArrayList<Direction> checkDirections(ArrayList<Enemy> monsterList, char[][] floorPlanMatrix) {
    ArrayList<Direction> possibleDirections = new ArrayList<>();
    Direction[] directionS = Direction.values();

    for (Direction direction : directionS) {
      if (canMoveInThisDirection(monsterList, floorPlanMatrix, direction)) {
        possibleDirections.add(direction);
      }
    }

    return possibleDirections;
  }

  private void makeAStep(Direction currentDirection) {
    this.yCoordinate += currentDirection.yModifier;
    this.xCoordinate += currentDirection.xModifier;
  }

  private boolean canMoveInThisDirection(ArrayList<Enemy> monsterList, char[][] floorPlanMatrix, Direction direction) {
    int proposedX = this.xCoordinate + direction.xModifier;
    int proposedY = this.yCoordinate + direction.yModifier;

    return  !wouldMoveOutsideTheBoard(proposedX, proposedY) &&
            !alreadyOccupiedByMonsters(monsterList, proposedX, proposedY) &&
             floorPlanMatrix[proposedX][proposedY] != 'O';
  }

  private static boolean wouldMoveOutsideTheBoard(int proposedX, int proposedY){
    return  proposedX > 9 || proposedX < 0 ||
            proposedY > 9 || proposedY < 0;
    }
  }
