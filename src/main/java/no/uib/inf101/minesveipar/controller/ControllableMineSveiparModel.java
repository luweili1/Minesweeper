package no.uib.inf101.minesveipar.controller;

import no.uib.inf101.grid.CellPosition;
import no.uib.inf101.grid.GridDimension;
import no.uib.inf101.minesveipar.model.GameState;

/**
 * The ControllableMineSveiparModel interface represents a controllable version
 * of the MineSveipar game model.
 * It provides methods to interact with the game model, such as placing mines,
 * uncovering cells, flagging mines,
 * getting the game state, and more.
 */
public interface ControllableMineSveiparModel {
    /**
     * Places mines randomly on the MineSveipar grid.
     */
    void placingMines();

    /**
     * Uncovers the cell at the specified position and returns its value.
     *
     * @param pos the position of the cell to uncover
     * @return the value of the uncovered cell
     */
    Integer uncoverCell(CellPosition pos);

    /**
     * Uncovers the surrounding cells of the specified position.
     *
     * @param pos the position of the cell to uncover surrounding cells for
     */
    void uncoverSurroundingCells(CellPosition pos);

    /**
     * Gets the dimension of the game grid.
     *
     * @return the dimension of the game grid
     */
    GridDimension getDimension();

    /**
     * Flags the mine at the specified position.
     *
     * @param pos the position of the mine to flag
     */
    void flagMine(CellPosition pos);

    /**
     * Checks if the cell at the specified position is flagged as a mine.
     *
     * @param pos the position of the cell to check
     * @return true if the cell is flagged as a mine, false otherwise
     */
    boolean isFlagged(CellPosition pos);

    /**
     * Gets the current game state.
     *
     * @return the current game state
     */
    GameState getGameState();

    /**
     * Sets the game state to the specified state.
     *
     * @param gameState the new game state
     */
    void setGameState(GameState gameState);

    /**
     * Gets the value of the cell at the specified position.
     *
     * @param pos the position of the cell
     * @return the value of the cell
     */
    int value(CellPosition pos);

    /**
     * Resets the game to its initial state.
     */
    void resetGame();
}