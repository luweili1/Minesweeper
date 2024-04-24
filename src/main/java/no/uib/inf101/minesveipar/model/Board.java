package no.uib.inf101.minesveipar.model;

import no.uib.inf101.grid.CellPosition;
import no.uib.inf101.grid.Grid;

/**
 * The Board class represents a game board for the Minesweeper game.
 * It extends the Grid class and uses MineCell objects to represent the cells on
 * the board.
 */
public class Board extends Grid<MineCell> {

    /**
     * Constructs a new Board object with the specified number of rows and columns.
     *
     * @param rows the number of rows in the board
     * @param cols the number of columns in the board
     */
    public Board(int rows, int cols) {
        super(rows, cols, new MineCell(0, true));
    }

    /**
     * Returns a string representation of the board, with each cell's value
     * separated by a newline character.
     *
     * @return a string representation of the board
     */
    public String prettyString() {
        StringBuilder result = new StringBuilder();

        for (int row = 0; row < this.rows(); row++) {
            for (int col = 0; col < this.cols(); col++) {
                result.append(get(new CellPosition(row, col)));
            }
            result.append(System.lineSeparator());
        }

        return result.toString().substring(0, result.length() - 1);
    }

    /**
     * Checks if the cell at the specified position is flagged.
     *
     * @param pos the position of the cell to check
     * @return true if the cell is flagged, false otherwise
     */
    public boolean isFlagged(CellPosition pos) {
        // Implementation not provided
        return true;
    }

    /**
     * Clears the board by setting all cells to new MineCell objects.
     */
    public void clear() {
        for (int row = 0; row < this.rows(); row++) {
            for (int col = 0; col < this.cols(); col++) {
                set(new CellPosition(row, col), new MineCell(0, true));
            }
        }
    }

}
