package no.uib.inf101.tetris.view;

import no.uib.inf101.grid.GridCell;
import no.uib.inf101.grid.GridDimension;
import no.uib.inf101.tetris.model.GameState;

public interface ViewableTetrisModel {
    
     /**
     * The dimensions of the board, i.e. number of rows and columns
     *
     * @return  an object of type GridDimension
     */
    GridDimension getDimension();

    /**
     * An object that when iterated over returns all positions
     * and corresponding vales
     *
     * @return  an iterable object
     */
    Iterable<GridCell<Character>> getTilesOnBoard();

    /**
     * Iterates over the tiles on the board
     *
     * @return an iterable object of type GridCell<Character>
     */
    Iterable<GridCell<Character>> getFallingPiece();

    /**
     * Tells us the state of the game
     *
     * @return the state of the game
     */
    GameState getGameState();

    
}
