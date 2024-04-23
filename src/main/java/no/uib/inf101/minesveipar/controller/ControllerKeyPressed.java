package no.uib.inf101.minesveipar.controller;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import no.uib.inf101.minesveipar.MineSveiparMain;
import no.uib.inf101.minesveipar.model.GameState;
import no.uib.inf101.minesveipar.view.MineSveiparView;

public class ControllerKeyPressed implements KeyListener {

    ControllableMineSveiparModel model;
    MineSveiparView view;

    public ControllerKeyPressed(ControllableMineSveiparModel model, MineSveiparView view) {
        this.model = model;
        this.view = view;

        this.view.setFocusable(true);
        this.view.addKeyListener(this);
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {

        if (model.getGameState() == GameState.WELCOME_SCREEN) {
            if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                model.setGameState(GameState.ACTIVE_GAME);
            }
        } else if (model.getGameState() == GameState.ACTIVE_GAME) {
            if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                this.view.repaint();
                MineSveiparMain.main(null);
            }
        } else if (model.getGameState() == GameState.GAME_OVER) {
            if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                this.view.repaint();
                MineSveiparMain.main(null);
            }
        }

        view.repaint();
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }

}
