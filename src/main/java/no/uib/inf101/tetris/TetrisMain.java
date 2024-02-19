package no.uib.inf101.tetris;

import javax.swing.JFrame;

import no.uib.inf101.tetris.controller.TetrisController;
import no.uib.inf101.tetris.model.TetrisBoard;
import no.uib.inf101.tetris.model.TetrisModel;
import no.uib.inf101.tetris.model.tetromino.RandomTetrominoFactory;
import no.uib.inf101.tetris.model.tetromino.TetrominoFactory;
import no.uib.inf101.tetris.view.TetrisView;


public class TetrisMain {

  public static final String WINDOW_TITLE = "INF101 Tetris";
  
  public static void main(String[] args) {
    TetrisBoard board = new TetrisBoard(15, 10);
    TetrominoFactory tetrominoFactory = new RandomTetrominoFactory();
    TetrisModel model = new TetrisModel(board, tetrominoFactory);
    TetrisView view = new TetrisView(model);
	new TetrisController(model, view);

    JFrame frame = new JFrame(WINDOW_TITLE);
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    
    frame.setContentPane(view);
    
    frame.pack();
    frame.setVisible(true);
  }
  
}
