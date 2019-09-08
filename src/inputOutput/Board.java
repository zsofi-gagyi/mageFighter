package inputOutput;

import game.Game;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class Board extends JComponent implements KeyListener {
  Game thisGame;

  public Board() {
    thisGame = new Game();
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
    thisGame.drawEverything(graphics);
  }

  @Override
  public void keyTyped(KeyEvent e) {  }

  @Override
  public void keyPressed(KeyEvent e) {  }

  @Override
  public void keyReleased(KeyEvent e) {

    if (e.getKeyCode() == KeyEvent.VK_UP) {
      ReactionToKeys.reactToKeys("up", thisGame);

    } else if (e.getKeyCode() == KeyEvent.VK_DOWN) {
      ReactionToKeys.reactToKeys("down", thisGame);

    }
    if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
      ReactionToKeys.reactToKeys("right", thisGame);

    } else if (e.getKeyCode() == KeyEvent.VK_LEFT) {
      ReactionToKeys.reactToKeys("left", thisGame);

    }
    if (e.getKeyCode() == KeyEvent.VK_SPACE) {
      ReactionToKeys.reactToKeys("space", thisGame);

    }

    repaint();
  }
}