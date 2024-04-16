package no.uib.inf101.minesveipar;

import javax.swing.JFrame;

import no.uib.inf101.minesveipar.controller.ControllerKeyPressed;
import no.uib.inf101.minesveipar.controller.MineSweeperController;
import no.uib.inf101.minesveipar.model.Board;
import no.uib.inf101.minesveipar.model.SveiparModel;
import no.uib.inf101.minesveipar.view.MineSweeperView;

public class MineSveiparMain {

  public static void main(String[] args) {
    Board board = new Board(25, 25);
    SveiparModel model = new SveiparModel(board);
    MineSweeperView view = new MineSweeperView(model);

    new MineSweeperController(model, view);
    new ControllerKeyPressed(model, view);

    JFrame frame = new JFrame();
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.setTitle("MineSweeper");
    frame.setContentPane(view);
    frame.pack();
    frame.setVisible(true);
  }

}
