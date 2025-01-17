package no.uib.inf101.minesveipar;

import javax.swing.JFrame;

import no.uib.inf101.minesveipar.controller.ControllerKeyPressed;
import no.uib.inf101.minesveipar.controller.MineSweeperController;
import no.uib.inf101.minesveipar.model.Board;
import no.uib.inf101.minesveipar.model.SveiparModel;
import no.uib.inf101.minesveipar.view.MineSveiparView;

/**
 * The main class for the MineSveipar game.
 * This class initializes the game components and starts the game.
 */
public class MineSveiparMain {

  public static void main(String[] args) {
    Board board = new Board(8, 8);
    SveiparModel model = new SveiparModel(board);
    MineSveiparView view = new MineSveiparView(model);

    new MineSweeperController(model, view);
    new ControllerKeyPressed(model, view);

    JFrame frame = new JFrame();
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.setTitle("MineSveipar");
    frame.setContentPane(view);
    frame.pack();
    frame.setVisible(true);
    frame.setResizable(false);
  }

}
