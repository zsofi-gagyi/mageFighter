package inputOutput;

import game.characters.Hero;
import game.characters.MushroomWithBox;
import game.characters.Managers.EnemyManager;
import game.Levels.Level;
import game.specialScreens.Battle;
import generalHelper.Direction;
import game.Game;

public class ReactionToKeys {

  public static void reactToKeys(String directionString, Game currentGame) {
    boolean permittedToMove;

    switch (directionString) {

      case "space":
        if (!currentGame.isShowingStatsBetweenLevels) {
          if (currentGame.hero.isFighting) {
            if (currentGame.battle.hasJustBegan) {
              currentGame.battle.hasJustBegan = false;
            } else if (currentGame.battle.isHerosTurn) {
              currentGame.hero.attack(currentGame);
              break;
            } else {
              currentGame.hero.getAttacked(currentGame);
            }
          }
        } else {
          currentGame.isShowingStatsBetweenLevels = false;
          currentGame.arrangeLevel();
        }
        break;

      default:
        Direction direction;
        switch (directionString){
          case "up":
            direction = Direction.UP;
            break;
          case "down":
            direction = Direction.DOWN;
            break;
          case "left":
            direction = Direction.LEFT;
            break;
          default:
            direction = Direction.RIGHT;
          }

        currentGame.hero.appearance = direction.heroAppearance;
        permittedToMove = hasPermissionToMove(direction, currentGame.hero, currentGame.level);

        if (permittedToMove) {
          currentGame.hero.xCoordinate += direction.xModifier;
          currentGame.hero.yCoordinate += direction.yModifier;
        }
    }

    if (!currentGame.hero.isFighting) {
      MushroomWithBox.interactWithMushroom(currentGame.mushroomWithBox, currentGame.hero, directionString);

      EnemyManager.checkMonsters(currentGame);

      if (!(currentGame.hero.isFighting)) {
        currentGame.enemyManager.moveMonsters(currentGame, currentGame.hero, currentGame.enemyManager, currentGame.battle,
                                                                                currentGame.level.floorPlanMatrix);
      }
    }
  }

  private static boolean hasPermissionToMove(Direction direction,  Hero hero, Level level) {
    return !hero.isFighting && !wouldRunIntoThings(direction, hero, level);
  }

  private static boolean wouldRunIntoThings(Direction direction, Hero hero, Level level) {

    int xCoordonate = direction.xModifier + hero.xCoordinate;
    int yCoordonate = direction.yModifier + hero.yCoordinate;

    if (wouldLeaveTheBoard (xCoordonate, yCoordonate)){
      return true;
    }

    return wouldRunIntoWall(level, xCoordonate, yCoordonate);
  }

  private static boolean wouldRunIntoWall(Level level, int xCoordonate, int yCoordonate) {
    return level.tiles[xCoordonate][yCoordonate].equals(level.currentWall);
  }

  private static boolean wouldLeaveTheBoard (int xCoordonate, int yCoordonate){
    return (xCoordonate > 9 || xCoordonate < 0 || yCoordonate > 9 || yCoordonate < 0);
  }
}
