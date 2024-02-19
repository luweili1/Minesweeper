package no.uib.inf101.tetris.model;

import no.uib.inf101.grid.Grid;
import no.uib.inf101.grid.GridCell;

public class TetrisBoard extends Grid<Character> {
    
    public TetrisBoard(int rows, int cols) {
        super(rows, cols, '-');
    }

    public String prettyString() {
        StringBuilder builder = new StringBuilder();

        int i = 0;
        for (GridCell<Character> cell : this) {
            char c = cell.value();
            builder.append(c);
            if (i == cols()-1) {
                builder.append("\n");
                i = 0;
                continue;
            }
            i++;
        }
        builder.deleteCharAt(builder.length()-1);
        return builder.toString();
    }

}
