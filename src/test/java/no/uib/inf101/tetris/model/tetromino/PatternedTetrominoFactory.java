package no.uib.inf101.tetris.model.tetromino;


public class PatternedTetrominoFactory implements TetrominoFactory {

	private String tetrominoPattern;
	private int index;

	public PatternedTetrominoFactory(String pattern) {
		this.tetrominoPattern = pattern;
		this.index = 0;
	}

	@Override
	public Tetromino getNext() {
		char current = tetrominoPattern.charAt(index % tetrominoPattern.length());
        index++;
        return Tetromino.newTetromino(current);
	}
	

}
