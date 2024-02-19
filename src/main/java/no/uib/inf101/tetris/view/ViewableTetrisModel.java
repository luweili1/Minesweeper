package no.uib.inf101.tetris.view;

import no.uib.inf101.grid.GridCell;
import no.uib.inf101.grid.GridDimension;
import no.uib.inf101.tetris.model.GameState;

public interface ViewableTetrisModel {
    
    public GridDimension getDimension();

    public Iterable<GridCell<Character>> getTilesOnBoard();

    public Iterable<GridCell<Character>> getFallingPiece();

	public GameState getGameState();
    
}
