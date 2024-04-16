package no.uib.inf101.minesveipar.model;

import no.uib.inf101.grid.CellPosition;
import no.uib.inf101.grid.Grid;

public class Board extends Grid<MineCell> {

    public Board(int rows, int cols) {
        super(rows, cols, new MineCell(0, true));
    }

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

    public boolean isFlagged(CellPosition pos) {

        return true;

    }

}
