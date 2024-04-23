package no.uib.inf101.minesveipar.controller;

import java.awt.event.MouseEvent;

import no.uib.inf101.grid.CellPosition;
import no.uib.inf101.minesveipar.view.MineSveiparView;
import no.uib.inf101.minesveipar.view.PixelToCellPositionConverter;

public class MineSweeperController implements java.awt.event.MouseListener {

    ControllableMineSveiparModel model;
    MineSveiparView view;

    public MineSweeperController(ControllableMineSveiparModel controll, MineSveiparView view) {
        this.model = controll;
        this.view = view;
        view.addMouseListener(this);
        view.setFocusable(true);
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        CellPosition pos = new CellPosition(e.getX(), e.getY());
        PixelToCellPositionConverter converter = new PixelToCellPositionConverter(this.view.getBox(),
                this.model.getDimension());
        pos = converter.getCellforPixel(pos);

        if (e.getButton() == MouseEvent.BUTTON1) {
            System.out.println("Left click at: " + pos);
            System.out.println("cellvalue: " + this.model.value());
            this.model.uncoverCell(pos);
            this.view.repaint();
            this.view.revalidate();

        }

        if (e.getButton() == MouseEvent.BUTTON3) {
            System.out.println("Right click at: " + pos);
            System.out.println("cellvalue: " + this.model.value());
            this.model.flagMine(pos);
            this.view.repaint();
            this.view.revalidate();

        }
    }

    @Override
    public void mousePressed(MouseEvent e) {

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
