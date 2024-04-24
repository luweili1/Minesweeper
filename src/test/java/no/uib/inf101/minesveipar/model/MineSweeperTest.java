package no.uib.inf101.minesveipar.model;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class MineSweeperTest {

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
            int cellValue = board[pos.getRow()][pos.getColumn()];
            return cellValue != -1;
        }
    }

    private static class CellPosition {
        private int row;
        private int column;

        public CellPosition(int row, int column) {
            this.row = row;
            this.column = column;
        }

        public int getRow() {
            return row;
        }

        public int getColumn() {
            return column;
        }
    }

}
