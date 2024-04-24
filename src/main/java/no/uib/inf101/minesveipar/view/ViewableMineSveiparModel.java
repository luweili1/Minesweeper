package no.uib.inf101.minesveipar.view;

import no.uib.inf101.grid.CellPosition;
import no.uib.inf101.grid.GridCell;
import no.uib.inf101.grid.GridDimension;
import no.uib.inf101.minesveipar.model.GameState;
import no.uib.inf101.minesveipar.model.MineCell;

/**
 * The ViewableMineSveiparModel interface represents a viewable model of the
 * MineSveipar game.
 * It provides methods to retrieve information about the game board and perform
 * game actions.
 */
public interface ViewableMineSveiparModel {

    /**
     * Returns the dimension of the game board.
     *
     * @return the dimension of the game board
     */
    GridDimension getDimension();

    /**
     * Returns an iterable of GridCell objects representing the tiles on the game
     * board.
     *
     * @return an iterable of GridCell objects representing the tiles on the game
     *         board
     */
    Iterable<GridCell<MineCell>> getTilesOnBoard();

    /**
     * Returns the current state of the game.
     *
     * @return the current state of the game
     */
    GameState getGameState();

    /**
     * Uncovers the cell at the specified position on the game board.
     *
     * @param pos the position of the cell to uncover
     * @return the number of adjacent mines, or null if the cell is a mine
     */
    Integer uncoverCell(CellPosition pos);

    /**
     * Uncovers the surrounding cells of the specified position on the game board.
     *
     * @param pos the position of the cell to uncover the surrounding cells of
     */
    void uncoverSurroundingCells(CellPosition pos);

    /**
     * Flags the mine at the specified position on the game board.
     *
     * @param pos the position of the mine to flag
     */
    void flagMine(CellPosition pos);

    /**
     * Returns the number of remaining mines on the game board.
     *
     * @return the number of remaining mines on the game board
     */
    int mineCounter();

}
