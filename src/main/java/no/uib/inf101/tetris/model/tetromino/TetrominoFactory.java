package no.uib.inf101.tetris.model.tetromino;

public interface TetrominoFactory {
    
     /**
     * Get a new Tetromino piece
     *
     * @return Tetromino
     */
    public Tetromino getNext();
}
