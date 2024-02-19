package no.uib.inf101.tetris.model.tetromino;

import java.util.Random;

public class RandomTetrominoFactory implements TetrominoFactory{

    private Random random = new Random();

    @Override
    public Tetromino getNext() {
        String tetrominoSymbols = "LJSZTIO";
        int randomIndex = random.nextInt(tetrominoSymbols.length());
        char symbol = tetrominoSymbols.charAt(randomIndex);
        return Tetromino.newTetromino(symbol);
    }
    
}
