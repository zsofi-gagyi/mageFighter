package game.Levels.helperClasses;
import generalHelper.Direction;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

public class RandomFloorPlan {
  public char[][] matrix = new char[10][10];
  ArrayList<Bud> budList = new ArrayList<>(); // the name comes from seeing these growing walls as plants

  public RandomFloorPlan(int density) {

    boolean allSquaresAreConnected = false;

    while (!allSquaresAreConnected) {

      for (int i = 0; i < matrix.length; i++) {
        for (int j = 0; j < matrix[0].length; j++)  {
          this.matrix[i][j] = ' ';
        }
      }

      Direction[] directions = Direction.values();

      for (int i = 0; i < density; i++) {
        Bud bud = new Bud(directions[i % 4]);
        if (!wouldGetTooCloseToSomething(bud)) {
          this.budList.add(bud);
        }
      }
      this.updateMatrix();
      this.growBranches();

      if (allSquaresConnected()) {
        allSquaresAreConnected = true;
      }
    }

    cleanUpAfterFloodFill();
  }


  public boolean allSquaresConnected() {
    int totalFloor = 0;

    for (char[] a : this.matrix) {
      for (char b : a) {
        if (b != 'O') {
          totalFloor++;
        }
      }
    }

    int[] initialCoordinates = findInitialCoordinates();
    int allCoordinatesReached = thisManySquaresAreConnectedTo(initialCoordinates);

    return (allCoordinatesReached == totalFloor);
  }


  public int[] findInitialCoordinates() {
    int[] result ={0, 0};

    while (this.matrix[result[0]][result[1]] == 'O') {
      result =  new int[] {(int)(Math.random() * 10),(int)(Math.random() * 10)};
    }

    return result;
  }

  public int thisManySquaresAreConnectedTo(int[] initialCoordinates) {
    floodFill(initialCoordinates);

    int totalFound = 0;

    for (char[] a : this.matrix) {
      for (char b : a) {
        if (b == 'X') {
          totalFound++;
        }
      }
    }

    return totalFound;
  }

  public void floodFill(int[] initialCoordinates) {
    Direction[] directions = Direction.values();
    Queue<int[]> nodes = new LinkedList<>();

    nodes.add(initialCoordinates);

    while (!nodes.isEmpty()){
      int[] activeNode = nodes.poll();
      this.matrix[activeNode[0]][activeNode[1]] = 'X';

      for (Direction direction : directions) {
        int[] newNode = { activeNode[0] + direction.xModifier, activeNode[1] + direction.yModifier};

        if ((newNode[0] <= 9) && (newNode[0] >= 0) &&
            (newNode[1] <= 9) && (newNode[1] >= 0) &&
             this.matrix[newNode[0]][newNode[1]] == ' '){
            nodes.add(newNode);
          }
        }
      }
    }

  public void growBranches() {

    while (this.budList.size() > 0) {
      ArrayList<Bud> newList = new ArrayList<>();

      for (Bud bud : this.budList) {
        if (wasAllowedAndDidGrow(bud)){
          newList.add(bud);
          this.updateMatrix();
        }
      }

      this.budList = newList;
    }
  }

  public boolean wasAllowedAndDidGrow(Bud bud) {
    if (willRemainInsideBoard(bud) && !wouldGetTooCloseToSomething(bud)) {
      this.grow(bud);
      return true;
    }
    return false;
  }

  public boolean wouldGetTooCloseToSomething(Bud bud) {

    int xCoordinate = bud.xCoordinate + bud.direction.xModifier;
    int yCoordinate = bud.yCoordinate + bud.direction.yModifier;

    if (this.matrix[xCoordinate][yCoordinate] == 'O' ||
        this.matrix[xCoordinate + bud.direction.xModifier][yCoordinate + bud.direction.yModifier] == 'O'){
      return true;
    }

    if (bud.direction.yModifier == 0 &&
        ( this.matrix[xCoordinate + bud.direction.xModifier][yCoordinate + 1] == 'O' ||
          this.matrix[xCoordinate + bud.direction.xModifier][yCoordinate - 1] == 'O' ||
          this.matrix[xCoordinate][yCoordinate + 1] == 'O' ||
          this.matrix[xCoordinate][yCoordinate - 1] == 'O' )){
      return true;
    }

    if (bud.direction.xModifier == 0 &&
        ( this.matrix[xCoordinate + 1][yCoordinate + bud.direction.yModifier] == 'O' ||
          this.matrix[xCoordinate - 1][yCoordinate  + bud.direction.yModifier] == 'O' ||
          this.matrix[xCoordinate + 1][yCoordinate] == 'O' ||
          this.matrix[xCoordinate - 1][yCoordinate] == 'O')){
      return true;
    }

    return false;
  }

  public boolean willRemainInsideBoard(Bud bud) {
    int xCoordinate = bud.xCoordinate + bud.direction.xModifier;
    int yCoordinate = bud.yCoordinate + bud.direction.yModifier;

    return  !(xCoordinate > 8 || xCoordinate < 1 ||
        yCoordinate > 8 || yCoordinate < 1);
  }

  private void grow(Bud bud) {
    bud.xCoordinate += bud.direction.xModifier;
    bud.yCoordinate += bud.direction.yModifier;
  }

  private void updateMatrix() {
    for (Bud bud : this.budList) {
      this.matrix[bud.xCoordinate][bud.yCoordinate] = 'O';
    }
  }

  private void cleanUpAfterFloodFill(){
    for (int i = 0; i < this.matrix.length; i++) {
      for (int j = 0; j < this.matrix[i].length; j++) {
        if (this.matrix[i][j] == 'X') {
          this.matrix[i][j] = ' ';
        }
      }
    }
  }
}