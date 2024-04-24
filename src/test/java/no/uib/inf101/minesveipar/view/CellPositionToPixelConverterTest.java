package no.uib.inf101.minesveipar.view;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.awt.geom.Rectangle2D;

import org.junit.jupiter.api.Test;

import no.uib.inf101.grid.CellPosition;
import no.uib.inf101.grid.GridDimension;
import no.uib.inf101.minesveipar.model.Board;

public class CellPositionToPixelConverterTest {

    @Test
    public void sanityTest() {
        GridDimension gd = new Board(3, 4);
        CellPositionToPixelConverter converter = new CellPositionToPixelConverter(
                new Rectangle2D.Double(29, 29, 340, 240), gd, 30);
        Rectangle2D expected = new Rectangle2D.Double(214.0, 119.0, 62.5, 60);
        assertEquals(expected, converter.getBoundsForCell(new CellPosition(1, 2)));
    }

    @Test
    public void testGetBoundsForCell() {
        GridDimension gd = new Board(5, 5);
        CellPositionToPixelConverter converter = new CellPositionToPixelConverter(
                new Rectangle2D.Double(0, 0, 100, 100), gd, 10);
        Rectangle2D expected = new Rectangle2D.Double(22, 22, 12, 12);
        assertEquals(expected, converter.getBoundsForCell(new CellPosition(1, 1)));
    }

    @Test
    public void testCellAtGridBoundary() {
        GridDimension gd = new Board(10, 10);
        CellPositionToPixelConverter converter = new CellPositionToPixelConverter(
                new Rectangle2D.Double(0, 0, 300, 300), gd, 10);

        Rectangle2D expected = new Rectangle2D.Double(279, 279, 21, 21);
        assertEquals(expected, converter.getBoundsForCell(new CellPosition(9, 9)));
    }

    @Test
    public void testZeroMargin() {
        GridDimension gd = new Board(3, 3);
        CellPositionToPixelConverter converter = new CellPositionToPixelConverter(
                new Rectangle2D.Double(10, 10, 90, 90), gd, 0);
        Rectangle2D expected = new Rectangle2D.Double(10, 10, 30, 30);
        assertEquals(expected, converter.getBoundsForCell(new CellPosition(0, 0)));
    }

    @Test
    public void testSingleCellGrid() {
        GridDimension gd = new Board(1, 1);
        CellPositionToPixelConverter converter = new CellPositionToPixelConverter(
                new Rectangle2D.Double(50, 50, 50, 50), gd, 5);
        Rectangle2D expected = new Rectangle2D.Double(50, 50, 50, 50);
        assertEquals(expected, converter.getBoundsForCell(new CellPosition(0, 0)));
    }

}
