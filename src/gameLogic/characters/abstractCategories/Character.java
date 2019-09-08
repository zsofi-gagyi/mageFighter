package gameLogic.characters.abstractCategories;

public class Character {
  public String name;
  public String appearance;
  public int healthPoint;
  public int defendPoint;
  public int strikePoint;
  public int maxHealth;
  public int xCoordinate;
  public int yCoordinate;

  public static int d6() {
    return (int)(Math.random() * 6) + 1;
  }
}
