package no.uib.inf101.tetris.model;

import no.uib.inf101.grid.CellPosition;
import no.uib.inf101.grid.GridCell;
import no.uib.inf101.grid.GridDimension;
import no.uib.inf101.tetris.controller.ControllableTetrisModel;
import no.uib.inf101.tetris.model.tetromino.Tetromino;
import no.uib.inf101.tetris.model.tetromino.TetrominoFactory;
import no.uib.inf101.tetris.view.ViewableTetrisModel;

public class TetrisModel implements ViewableTetrisModel, ControllableTetrisModel {

    private TetrisBoard board;
    private TetrominoFactory tetrominoFactory;

    private Tetromino fallingPiece;

    public TetrisModel(TetrisBoard board, TetrominoFactory tetrominoFactory) {
        this.board = board;
        this.tetrominoFactory = tetrominoFactory;
        this.fallingPiece = tetrominoFactory.getNext().shiftedToTopCenterOf(board);
    }

	@Override
	public boolean moveTetromino(int deltaRow, int deltaCol) {
		Tetromino candidate = fallingPiece.shiftedBy(deltaRow, deltaCol);
		if (legalMove(candidate)) {
			fallingPiece = candidate;
			return true;
		}
		return false;
	}

	private boolean legalMove(Tetromino piece) {
        for (GridCell<Character> cell : piece) {
            if (!board.positionIsOnGrid(cell.pos()))
                return false;
            char charOnBoard = board.get(cell.pos());
            if (charOnBoard != '-')
                return false;
        }
        return true;
    }

    @Override
    public GridDimension getDimension() {
        return board;
    }

    @Override
    public Iterable<GridCell<Character>> getTilesOnBoard() {
        return board;
    }

    @Override
    public Iterable<GridCell<Character>> getFallingPiece() {
        return fallingPiece;
    }

	
    
}
