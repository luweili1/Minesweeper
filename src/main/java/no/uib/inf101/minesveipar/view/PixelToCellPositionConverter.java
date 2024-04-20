package no.uib.inf101.minesveipar.view;

import java.awt.geom.Rectangle2D;

import no.uib.inf101.grid.CellPosition;
import no.uib.inf101.grid.GridDimension;

public class PixelToCellPositionConverter {
    GridDimension gd;
    CellPosition pos;
    Rectangle2D box;

    public PixelToCellPositionConverter(Rectangle2D box, GridDimension gd) {
        this.box = box;
        this.gd = gd;

    }

    public CellPosition getCellforPixel(CellPosition pos) {
        int rows = gd.rows();
        int cols = gd.cols();

        int heightOfCell = (int) this.box.getHeight() / rows;
        int widthOfCell = (int) this.box.getWidth() / cols;

        int cell_x = (int) Math.floor(pos.col() / heightOfCell) - 2;
        int cell_y = (int) Math.floor(pos.row() / widthOfCell);

        CellPosition cp = new CellPosition(cell_x + 1, cell_y);
        return cp;
    }
}
