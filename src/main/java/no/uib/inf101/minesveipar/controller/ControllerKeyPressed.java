package no.uib.inf101.minesveipar.controller;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.Timer;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import no.uib.inf101.minesveipar.model.GameState;
import no.uib.inf101.minesveipar.view.MineSveiparView;

/**
 * The ControllerKeyPressed class is responsible for handling key events in the
 * MineSveipar game.
 * It implements the KeyListener interface to listen for key events.
 */
public class ControllerKeyPressed implements KeyListener {

    private ControllableMineSveiparModel model;
    private MineSveiparView view;
    private Timer gameTimer;
    private int elapsedTime;

    /**
     * Constructs a ControllerKeyPressed object with the specified model and view.
     * Initializes the elapsed time to 0 and sets up the key listener and timer.
     *
     * @param model the ControllableMineSveiparModel object
     * @param view  the MineSveiparView object
     */
    public ControllerKeyPressed(ControllableMineSveiparModel model, MineSveiparView view) {
        this.model = model;
        this.view = view;
        this.elapsedTime = 0;

        this.view.setFocusable(true);
        this.view.addKeyListener(this);
        initializeTimer();
    }

    /**
     * Initializes the game timer.
     * If the timer is already running, it stops the timer before initializing a new
     * one.
     */
    private void initializeTimer() {
        if (gameTimer != null) {
            gameTimer.stop();
        }
        gameTimer = new Timer(1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                elapsedTime++;
                view.repaint();
            }
        });
    }

    /**
     * Starts the game timer.
     * Resets the elapsed time to 0 and starts the timer if it is not already
     * running.
     */
    public void startTimer() {
        elapsedTime = 0;
        if (!gameTimer.isRunning()) {
            gameTimer.start();
        }
    }

    /**
     * Stops the game timer if it is running.
     */
    public void stopTimer() {
        if (gameTimer.isRunning()) {
            gameTimer.stop();
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            if (model.getGameState() == GameState.WELCOME_SCREEN) {
                model.setGameState(GameState.ACTIVE_GAME);
                view.startTimer();
            } else if (model.getGameState() == GameState.ACTIVE_GAME || model.getGameState() == GameState.GAME_OVER
                    || model.getGameState() == GameState.GAME_WON) {
                view.stopTimer();
                restartGame();
            }
        }
    }

    /**
     * Restarts the game.
     * Resets the game state, stops the timer, starts the timer again, and sets the
     * game state to active.
     */
    private void restartGame() {
        model.resetGame();
        view.stopTimer();
        view.startTimer();
        model.setGameState(GameState.ACTIVE_GAME);

    }

    @Override
    public void keyReleased(KeyEvent e) {
    }
}
