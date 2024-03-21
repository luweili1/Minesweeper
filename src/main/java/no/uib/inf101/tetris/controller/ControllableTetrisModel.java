package no.uib.inf101.tetris.controller;

import no.uib.inf101.tetris.model.GameState;

public interface ControllableTetrisModel {

	/**
     * Method to help move around the tetromino on the board
     * The new position of the tetromino needs to be within bounds of the board
     *
     * @param   deltaRow  number of rows to move the piece
     * @param   deltaCol  number of columns to move the piece
     * @return  true if the move happened, otherwise return false
     */
    boolean moveTetromino(int deltaRow, int deltaCol);

    /**
     * Rotate the piece to the left
     * The new position of the tetromino needs to be within bounds of the board
     *
     * @return  true if the piece was rotated, otherwise return false
     */
    boolean rotateFallingPiece();

    /**
     * Drop the falling piece and glue it to the board.
     */
    void dropFallingPiece();

    /**
     * Tells us the state of the game
     *
     * @return the state of the game
     */
    GameState getGameState();

    /**
     * Get the time between each clock tick in ms
     *
     * @return time in ms
     */
    int dropRate();

    /**
     * The method will be called each time the clock ticks.
     * This means moving the piece one row downwards if possible,
     * otherwise glue it to the board.
     */
    void clockTick();
}