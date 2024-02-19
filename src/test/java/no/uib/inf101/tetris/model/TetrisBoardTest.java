package no.uib.inf101.tetris.model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import no.uib.inf101.grid.CellPosition;

public class TetrisBoardTest {

    @Test
    public void testPrettyString() {
        TetrisBoard board = new TetrisBoard(3, 4);
        board.set(new CellPosition(0, 0), 'L');
        board.set(new CellPosition(0, 3), 'S');
        board.set(new CellPosition(2, 0), 'Z');
        board.set(new CellPosition(2, 3), 'O');
        String expected = String.join("\n", new String[] {
                "L--S",
                "----",
                "Z--O"
        });
        assertEquals(expected, board.prettyString());
    }

}
