package no.uib.inf101.tetris.controller;

import no.uib.inf101.tetris.view.TetrisView;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class TetrisController implements KeyListener {

	private ControllableTetrisModel model;
	private TetrisView view;

	public TetrisController(ControllableTetrisModel controllableModel, TetrisView view) {
		this.model = controllableModel;
		this.view = view;

		view.addKeyListener(this);
		
	}

	@Override
	public void keyPressed(KeyEvent e) {
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
            model.rotateTetromino();
        }
        view.repaint();
	}

	@Override
	public void keyTyped(KeyEvent e) {}

	@Override
	public void keyReleased(KeyEvent e) {}
	
}
