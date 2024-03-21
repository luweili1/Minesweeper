package no.uib.inf101.tetris.controller;

import no.uib.inf101.tetris.model.GameState;
import no.uib.inf101.tetris.view.TetrisView;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.Timer;

public class TetrisController implements KeyListener {

	private ControllableTetrisModel model;
	private TetrisView view;
	private Timer timer;

	public TetrisController(ControllableTetrisModel controllableModel, TetrisView view) {
		this.model = controllableModel;
		this.view = view;

		view.addKeyListener(this);

		this.timer = new Timer(model.dropRate(), this::clockTick);
        timer.start();
	}

	@Override
	public void keyPressed(KeyEvent e) {
		if (model.getGameState() == GameState.GAME_OVER)
			return;

		if (e.getKeyCode() == KeyEvent.VK_LEFT) {
            model.moveTetromino(0, -1);
        }
        else if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
            model.moveTetromino(0, 1);
        }
        else if (e.getKeyCode() == KeyEvent.VK_DOWN) {
            model.moveTetromino(1, 0);
        }
		else if (e.getKeyCode() == KeyEvent.VK_UP) {
            model.rotateFallingPiece();
        }
		else if (e.getKeyCode() == KeyEvent.VK_SPACE) {
            model.dropFallingPiece();
        }
        view.repaint();
	}

	public void clockTick (ActionEvent e) {
        if (this.model.getGameState() == GameState.ACTIVE_GAME) {
            this.model.clockTick();
            this.view.repaint();
        }
    }

	@Override
	public void keyTyped(KeyEvent e) {}

	@Override
	public void keyReleased(KeyEvent e) {}
	
}
