package no.uib.inf101.minesveipar.view;

import java.awt.geom.Rectangle2D;

import no.uib.inf101.grid.CellPosition;
import no.uib.inf101.grid.GridDimension;

/**
 * The CellPositionToPixelConverter class is responsible for converting cell
 * positions to pixel coordinates
 * within a given grid dimension and bounding box.
 */
public class CellPositionToPixelConverter {
    Rectangle2D box;
    GridDimension gd;
    double margin;

    /**
     * Constructs a CellPositionToPixelConverter object with the specified
     * parameters.
     *
     * @param box    the bounding box in which the grid is contained
     * @param gd     the grid dimension (number of rows and columns)
     * @param margin the margin between cells
     */
    public CellPositionToPixelConverter(Rectangle2D box, GridDimension gd, double margin) {
        this.box = box;
        this.gd = gd;
        this.margin = margin;
    }

    /**
     * Returns the bounds (rectangle) for the specified cell position.
     *
     * @param cp the cell position
     * @return the bounds (rectangle) for the specified cell position
     */
    public Rectangle2D getBoundsForCell(CellPosition cp) {
        double cellWidth = (box.getWidth() - (this.margin * (gd.cols()))) / gd.cols();
        double cellHeight = (box.getHeight() - (this.margin * (gd.rows()))) / gd.rows();
        double cellX = box.getX() + (this.margin * (cp.col())) + (cellWidth * cp.col());
        double cellY = box.getY() + (this.margin * (cp.row()) + (cellHeight * cp.row()));

        return new Rectangle2D.Double(cellX, cellY, cellWidth, cellHeight);
    }
}
