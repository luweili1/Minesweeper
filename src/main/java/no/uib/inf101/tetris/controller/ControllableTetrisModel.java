package no.uib.inf101.tetris.controller;

import no.uib.inf101.tetris.model.GameState;

public interface ControllableTetrisModel {

	public boolean moveTetromino(int deltaRow, int deltaCol);

	public boolean rotateTetromino();

	public void dropTetromino();

	public GameState getGameState();
	
}
