package no.uib.inf101.tetris.model.tetromino;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;

import no.uib.inf101.grid.CellPosition;
import no.uib.inf101.grid.GridCell;
import no.uib.inf101.grid.GridDimension;

public class Tetromino implements Iterable<GridCell<Character>> {
    
    private char symbol;
    private boolean[][] shape;
    private CellPosition pos;

    private Tetromino(char symbol, boolean[][] shape, CellPosition pos) {
        this.symbol = symbol;
        this.shape = shape;
        this.pos = pos;
    }

    public Tetromino shiftedBy(int deltaRow, int deltaCol) {
        CellPosition newPos = new CellPosition(pos.row()+deltaRow, pos.col()+deltaCol);
        return new Tetromino(symbol, shape, newPos);
    }

    public Tetromino shiftedToTopCenterOf(GridDimension gd) {
        CellPosition newPos = new CellPosition(0, (gd.cols()/2)-2);
        return new Tetromino(symbol, shape, newPos);
    }

	public Tetromino rotate() {
		boolean[][] newShape = new boolean[this.shape.length][this.shape[0].length];
		for (int row = 0; row < this.shape.length; row++) {
			for (int col = 0; col < this.shape[0].length; col++) {
				int newRow = col;
                int newCol = shape[0].length - 1 - row;
				newShape[row][col] = this.shape[newRow][newCol];
			}
		}
		return new Tetromino(symbol, newShape, pos);
	}

    static Tetromino newTetromino(char sym) {
        boolean[][] shape;
        switch (sym) {
            case 'I' -> shape = new boolean[][] {
                { false, false, false, false },
                {  true,  true,  true,  true },
                { false, false, false, false },
                { false, false, false, false },
              };
              case 'O' -> shape = new boolean[][] {
                { false, false, false, false },
                { false,  true,  true, false },
                { false,  true,  true, false },
                { false, false, false, false },
              };
              case 'T' -> shape = new boolean[][] {
                { false, false, false },
                {  true,  true,  true },
                { false,  true, false }
              };
              case 'J' -> shape = new boolean[][] {
                { false, false, false },
                {  true,  true,  true },
                { false, false,  true }
              };
              case 'L' -> shape = new boolean[][] {
                { false, false, false },
                {  true,  true,  true },
                {  true, false, false }
              };
              case 'S' -> shape = new boolean[][] {
                { false, false, false },
                { false,  true,  true },
                {  true,  true, false }
              };
              case 'Z' -> shape = new boolean[][] {
                { false, false, false },
                {  true,  true, false },
                { false,  true,  true }
              };
              default -> throw new IllegalArgumentException(
              "Unknown tetromino type: '" + sym + "'"
              );
        }
        return new Tetromino(sym, shape, new CellPosition(0, 0));
    }

    @Override
    public Iterator<GridCell<Character>> iterator() {
        List<GridCell<Character>> list = new ArrayList<>();
        for (int i = 0; i < shape.length; i++) {
            for (int j = 0; j < shape[0].length; j++) {
                if (shape[i][j]) {
                    CellPosition pos = new CellPosition(this.pos.row()+i, this.pos.col()+j);
                    list.add(new GridCell<Character>(pos, symbol));
                }
            }
        }
        return list.iterator();
    }

    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof Tetromino)) {
            return false;
        }
        Tetromino tetromino = (Tetromino) o;
        return symbol == tetromino.symbol && Arrays.deepEquals(shape, tetromino.shape) && Objects.equals(pos, tetromino.pos);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.symbol, Arrays.deepHashCode(this.shape), this.pos);
    }


}
