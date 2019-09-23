package inputOutput;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class ImageDrawer {

  BufferedImage image;
  int posX, posY;

  public ImageDrawer(String filename, int posX, int posY) {
    this.posX = posX;
    this.posY = posY;
    try {
      image = ImageIO.read(this.getClass().getResource(filename));
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  public void draw(Graphics graphics) {
    if (image != null) {
      graphics.drawImage(image, posX, posY, null);
    }
  }
}