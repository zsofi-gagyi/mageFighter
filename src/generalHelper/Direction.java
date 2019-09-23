package generalHelper;

public enum Direction {
  UP ("up", 0, -1, "/hero-up.png"),
  DOWN ("down", 0, 1, "/hero-down.png"),
  LEFT("left", -1, 0, "/hero-left.png"),
  RIGHT("right", 1, 0, "/hero-right.png");

  public final String name;
  public final int xModifier;
  public final int yModifier;
  public final String heroAppearance;

  Direction(String name, int xModifier, int yModifier, String heroAppearance) {
    this.name = name;
    this.xModifier = xModifier;
    this.yModifier = yModifier;
    this.heroAppearance = heroAppearance;
  }
}
