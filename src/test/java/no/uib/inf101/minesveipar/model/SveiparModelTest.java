package no.uib.inf101.minesveipar.model;

import static org.junit.jupiter.api.Assertions.*;

import no.uib.inf101.grid.CellPosition;

import org.junit.jupiter.api.Test;

public class SveiparModelTest {

    @Test
    public static void main(String[] args) {
        Board board = new Board(5, 5);

        SveiparModel model = new SveiparModel(board);

        model.resetGame();

    }

    @Test
    public void testFlaggingAndUnflagging() {
        SveiparModel model = new SveiparModel(new Board(10, 10));
        CellPosition position = new CellPosition(5, 5);
        model.flagMine(position);
        assertTrue(model.isFlagged(position));
        model.flagMine(position);
        assertFalse(model.isFlagged(position));
    }

    @Test
    public void testFlagLimit() {
        SveiparModel model = new SveiparModel(new Board(10, 10));
        for (int i = 0; i < model.NUM_MINES; i++) {
            model.flagMine(new CellPosition(i, 0));
        }
        assertEquals(0, model.mineCounter());
    }

    @Test
    public void testCanPlaceMines() {
        int[][] mockBoard = {
                { 0, 1, 2 },
                { 3, -1, 4 },
                { 5, 6, 7 }
        };

        MinePlacement mockMinePlacement = new MinePlacement(mockBoard);

        assertTrue(mockMinePlacement.canPlaceMines(new CellPosition(0, 0)));
        assertTrue(mockMinePlacement.canPlaceMines(new CellPosition(1, 2)));
        assertTrue(mockMinePlacement.canPlaceMines(new CellPosition(2, 1)));
        assertFalse(mockMinePlacement.canPlaceMines(new CellPosition(1, 1)));
    }

    private static class MinePlacement {
        private int[][] board;

        public MinePlacement(int[][] board) {
            this.board = board;
        }

        public boolean canPlaceMines(CellPosition pos) {
            int cellValue = board[pos.row()][pos.col()];
            return cellValue != -1;
        }
    }

    @Test
    public void testRevealMine() {
        SveiparModel model = new SveiparModel(new Board(10, 10));
        model.board.set(new CellPosition(3, 3), new MineCell(model.MINE_VALUE, true));
        model.uncoverCell(new CellPosition(3, 3));
        assertEquals(GameState.GAME_OVER, model.getGameState());
    }

    @Test
    public void testUncoverCell() {
        Board board = new Board(9, 9);
        SveiparModel model = new SveiparModel(board);

        board.set(new CellPosition(0, 0), new MineCell(-1, true));
        board.set(new CellPosition(0, 1), new MineCell(0, true));
        board.set(new CellPosition(1, 0), new MineCell(0, true));
        board.set(new CellPosition(1, 1), new MineCell(-1, true));

        assertEquals(Integer.valueOf(-1), model.uncoverCell(new CellPosition(0, 0)),
                "Should return 0 for no adjacent mines");
        assertEquals(Integer.valueOf(0), model.uncoverCell(new CellPosition(0, 1)),
                "Should return 1 for one adjacent mine");

    }

}
