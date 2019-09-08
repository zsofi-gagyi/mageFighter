package inputOutput;

import gameLogic.Game;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class Board extends JComponent implements KeyListener {
  Game game;

  public Board() {
    this.game = new Game();
    setPreferredSize(new Dimension(720, 820));
    setVisible(true);
  }

  public static void main(String[] args) {
    JFrame frame = new JFrame("mageFighter.game");
    Board board = new Board();
    frame.add(board);
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.setVisible(true);
    frame.pack();
    frame.addKeyListener(board);
  }

  @Override
  public void paint(Graphics graphics) {
    super.paint(graphics);
    game.drawEverything(graphics);
  }

  @Override
  public void keyTyped(KeyEvent e) {  }

  @Override
  public void keyPressed(KeyEvent e) {  }

  @Override
  public void keyReleased(KeyEvent e) {
    int input = e.getKeyCode();

    if (input == KeyEvent.VK_UP) {
      InputProcesser.reactToKeys("up", this.game);
    }

    if (input == KeyEvent.VK_DOWN) {
      InputProcesser.reactToKeys("down", this.game);
    }

    if (input == KeyEvent.VK_RIGHT) {
      InputProcesser.reactToKeys("right", this.game);
    }

    if (input == KeyEvent.VK_LEFT) {
      InputProcesser.reactToKeys("left", this.game);
    }

    if (input == KeyEvent.VK_SPACE) {
      InputProcesser.reactToKeys("space", this.game);
    }

    repaint();
  }
}