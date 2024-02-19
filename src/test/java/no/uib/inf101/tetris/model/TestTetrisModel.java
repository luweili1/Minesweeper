package no.uib.inf101.tetris.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

import no.uib.inf101.grid.CellPosition;
import no.uib.inf101.grid.GridCell;
import no.uib.inf101.tetris.model.tetromino.PatternedTetrominoFactory;
import no.uib.inf101.tetris.model.tetromino.TetrominoFactory;
import no.uib.inf101.tetris.view.ViewableTetrisModel;

public class TestTetrisModel {
	
	@Test
	public void initialPositionOfO() {
		TetrisBoard board = new TetrisBoard(20, 10);
		TetrominoFactory factory = new PatternedTetrominoFactory("O");
		ViewableTetrisModel model = new TetrisModel(board, factory);

		List<GridCell<Character>> tetroCells = new ArrayList<>();
		for (GridCell<Character> gc : model.getFallingPiece()) {
			tetroCells.add(gc);
			System.out.println(gc.pos());
		}

		assertEquals(4, tetroCells.size());
		assertTrue(tetroCells.contains(new GridCell<>(new CellPosition(1, 4), 'O')));
		assertTrue(tetroCells.contains(new GridCell<>(new CellPosition(1, 5), 'O')));
		assertTrue(tetroCells.contains(new GridCell<>(new CellPosition(2, 4), 'O')));
		assertTrue(tetroCells.contains(new GridCell<>(new CellPosition(2, 5), 'O')));
	}

}
