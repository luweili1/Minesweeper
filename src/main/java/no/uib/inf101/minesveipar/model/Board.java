package no.uib.inf101.minesveipar.model;

import no.uib.inf101.grid.Grid;

public class Board extends Grid<MineCell> {

    public Board(int rows, int cols) {
        super(rows, cols, new MineCell(0, true));
    }

}
