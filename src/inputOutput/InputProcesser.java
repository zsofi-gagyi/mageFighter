package inputOutput;

import gameLogic.characters.MushroomWithBox;
import gameLogic.characters.managers.EnemyManager;
import gameLogic.specialScreens.Battle;
import generalHelper.Direction;
import gameLogic.Game;

public class InputProcesser { // as in, this processes the input [key presses]

  public static void reactToKeys(String directionString, Game currentGame) {
    boolean permittedToMove;

    switch (directionString) {

      case "space":

        switch (currentGame.gameState){
          case battle:

            switch (currentGame.battle.battleState){
              case willBeginWithHero:
                currentGame.battle.battleState = Battle.BattleState.herosTurn;
                break;

              case enemysTurn:
                currentGame.hero.getAttacked(currentGame);
                break;

              case willBeginWithEnemy:
                currentGame.battle.battleState = Battle.BattleState.enemysTurn;
                break;

              case herosTurn:
                currentGame.hero.attack(currentGame);
                break;

              case over:
                currentGame.gameState = Game.GameState.board;
                break;
            }
            break;

          case textScreen:
            currentGame.gameState = Game.GameState.board;
            currentGame.arrangeLevel();
            break;
        }
        break;

      default:
        if (currentGame.gameState.equals(Game.GameState.board)) {

          Direction direction;
          switch (directionString) {
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
          currentGame.hero.moveIfPermitted(direction, currentGame);

        }
    }

    if (currentGame.gameState.equals(Game.GameState.board)) {
      EnemyManager.checkMonsters(currentGame);

        if (currentGame.gameState.equals(Game.GameState.board)) { // if the hero didn't run into a monster

        MushroomWithBox.interactWithMushroom(currentGame.mushroomWithBox, currentGame.hero, directionString);
        currentGame.enemyManager.moveMonsters(currentGame);
      }
    }
  }
}
