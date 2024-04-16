package no.uib.inf101.minesveipar.model;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import no.uib.inf101.grid.CellPosition;

public class BoardTest {

    @Test
    public void testBoardInitialization() {
        Board board = new Board(10, 10);
        assertEquals(10, board.rows());
        assertEquals(10, board.cols());
    }

    @Test
    public void testPrettyStringAllHidden() {
        // Board with all hidden cells (default value)
        Board board = new Board(3, 4);
        String expected = String.join("\n", new String[] {
                "----",
                "----",
                "----"
        });
        assertEquals(expected, board.prettyString());
    }

    @Test
    public void testUncoverCellNoMine() {
        Board board = new Board(5, 5);
        SveiparModel model = new SveiparModel(board);
        CellPosition pos = new CellPosition(2, 2);

        model.board.set(pos, new MineCell(1, true));

        int revealedValue = model.uncoverCell(pos);

        assertFalse(model.isHidden(pos));
        assertEquals(1, revealedValue);
    }

}
