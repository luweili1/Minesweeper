package no.uib.inf101.minesveipar.controller;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import no.uib.inf101.minesveipar.MineSveiparMain;
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
        if (e.getKeyCode() == KeyEvent.VK_SPACE) {

            this.view.repaint();
            MineSveiparMain.main(null);
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }

}
