package no.uib.inf101.tetris.model;

import no.uib.inf101.grid.CellPosition;
import no.uib.inf101.grid.Grid;

public class TetrisBoard extends Grid<Character> {
    
    public TetrisBoard(int rows, int cols) {
        super(rows, cols, '-');
    }

    public int clearRows() {
        int nRowsRemoved = 0;
        while (true) {
            if (clearRow())
                nRowsRemoved++;
            else
                break;
        }
        return nRowsRemoved;
    }

    private boolean clearRow() {
        for (int i = 0; i < rows(); i++) {
            if (!existsOnRow(i, '-')) {
                for (int j = i; j > 0; j--) {
                    copyRowTo(j-1, j);
                }
                return true;
            }
        }
        return false;
    }

    private boolean existsOnRow(int row, Character c) {
        for (int i = 0; i < cols(); i++) {
            Character currentTile = get(new CellPosition(row, i));
            if (c.equals(currentTile))
                return true;
        }
        return false;
    }

    private void copyRowTo(int row1, int row2) {
        for (int i = 0; i < cols(); i++) {
            Character currentTile = get(new CellPosition(row1, i));
            set(new CellPosition(row2, i), currentTile);
        }
    }

    /**
     * A string representation of the board in a pretty way
     * For test purposes
     *
     * @return  a string representation of the board
     */
    public String prettyString() {
        String pretty = "";
        for (int i = 0; i < this.rows(); i++) {
            for (int j = 0; j < this.cols(); j++) {
                pretty += this.get(new CellPosition(i,j));
            }
            pretty += "\n";
        }
        return pretty.strip();
    }

}
