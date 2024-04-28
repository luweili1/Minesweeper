package no.uib.inf101.minesveipar.controller;

import java.awt.event.MouseEvent;

import no.uib.inf101.grid.CellPosition;
import no.uib.inf101.minesveipar.model.GameState;
import no.uib.inf101.minesveipar.view.MineSveiparView;
import no.uib.inf101.minesveipar.view.PixelToCellPositionConverter;

/**
 * The MineSweeperController class is responsible for handling user input and
 * updating the model and view accordingly.
 * It implements the java.awt.event.MouseListener interface to listen for mouse
 * events.
 */
public class MineSweeperController implements java.awt.event.MouseListener {

    ControllableMineSveiparModel model;
    MineSveiparView view;

    /**
     * Constructs a MineSweeperController object with the specified model and view.
     * Registers itself as a mouse listener to the view.
     * 
     * @param controll the controllable mine sveipar model
     * @param view     the mine sveipar view
     */
    public MineSweeperController(ControllableMineSveiparModel controll, MineSveiparView view) {
        this.model = controll;
        this.view = view;
        view.addMouseListener(this);
        view.setFocusable(true);
    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {
        if (model.getGameState() != GameState.ACTIVE_GAME) {
            return;
        }

        CellPosition pos = new CellPosition(e.getX(), e.getY());
        PixelToCellPositionConverter converter = new PixelToCellPositionConverter(this.view.getBox(),
                this.model.getDimension());
        pos = converter.getCellforPixel(pos);

        if (e.getButton() == MouseEvent.BUTTON1) {
            this.model.uncoverCell(pos);
            this.view.repaint();
            this.view.revalidate();

        } else if (e.getButton() == MouseEvent.BUTTON3) {
            this.model.flagMine(pos);
            this.view.repaint();
            this.view.revalidate();
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

}
