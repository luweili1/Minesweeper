package no.uib.inf101.minesveipar.view;

import java.awt.geom.Rectangle2D;

import no.uib.inf101.grid.CellPosition;
import no.uib.inf101.grid.GridDimension;

public class CellPositionToPixelConverter {
    Rectangle2D box;
    GridDimension gd;
    double margin;

    public CellPositionToPixelConverter(Rectangle2D g2D, GridDimension gd, double margin) {
        this.box = g2D;
        this.gd = gd;
        this.margin = margin;
    }

    public Rectangle2D getBoundsForCell(CellPosition cp) {
        double cellWidth = (box.getWidth() - (this.margin * (gd.cols()))) / gd.cols();
        double cellHeight = (box.getHeight() - (this.margin * (gd.rows()))) / gd.rows();
        double cellX = box.getX() + (this.margin * (cp.col())) + (cellWidth * cp.col());
        double cellY = box.getY() + (this.margin * (cp.row()) + (cellHeight * cp.row()));

        return new Rectangle2D.Double(cellX, cellY, cellWidth, cellHeight);
    }
}
