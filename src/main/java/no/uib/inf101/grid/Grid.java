package no.uib.inf101.grid;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * Represents a grid data structure that stores elements of type E.
 * The grid is a two-dimensional collection of cells, where each cell can hold
 * an element.
 *
 * @param <E> the type of elements stored in the grid
 */
public class Grid<E> implements IGrid<E> {
    int rows;
    int cols;

    private ArrayList<ArrayList<E>> grid;

    /**
     * Constructs a new grid with the specified number of rows and columns.
     *
     * @param rows the number of rows in the grid
     * @param cols the number of columns in the grid
     */
    public Grid(int rows, int cols) {
        this(rows, cols, null);
    }

    /**
     * Constructs a new grid with the specified number of rows and columns,
     * and initializes all cells with the specified default value.
     *
     * @param rows         the number of rows in the grid
     * @param cols         the number of columns in the grid
     * @param defaultValue the default value to initialize all cells with
     */
    public Grid(int rows, int cols, E defaultValue) {
        this.grid = new ArrayList<>();
        this.rows = rows;
        this.cols = cols;
        for (int i = 0; i < rows; i++) {
            this.grid.add(new ArrayList<>());
            for (int j = 0; j < cols; j++) {
                this.grid.get(i).add(defaultValue);
            }
        }
    }

    /**
     * Returns the number of rows in the grid.
     *
     * @return the number of rows in the grid
     */
    @Override
    public int rows() {
        return this.rows;
    }

    /**
     * Returns the number of columns in the grid.
     *
     * @return the number of columns in the grid
     */
    @Override
    public int cols() {
        return this.cols;
    }

    /**
     * Returns an iterator over the cells in the grid.
     *
     * @return an iterator over the cells in the grid
     */
    @Override
    public Iterator<GridCell<E>> iterator() {
        ArrayList<GridCell<E>> result = new ArrayList<>();
        for (int i = 0; i < this.rows; i++) {
            for (int j = 0; j < this.cols; j++) {
                GridCell<E> cell = new GridCell<E>(new CellPosition(i, j), this.grid.get(i).get(j));
                result.add(cell);
            }
        }
        return result.iterator();
    }

    /**
     * Sets the value of the cell at the specified position in the grid.
     *
     * @param pos   the position of the cell
     * @param value the value to set
     * @throws IndexOutOfBoundsException if the position is outside the bounds of
     *                                   the grid
     */
    public void set(CellPosition pos, E value) {
        if (positionIsOnGrid(pos)) {
            this.grid.get(pos.row()).set(pos.col(), value);
        } else {
            throw new IndexOutOfBoundsException("Setting grid out of bounds at " + pos);
        }
    }

    /**
     * Returns the value of the cell at the specified position in the grid.
     *
     * @param pos the position of the cell
     * @return the value of the cell at the specified position
     * @throws IndexOutOfBoundsException if the position is outside the bounds of
     *                                   the grid
     */
    @Override
    public E get(CellPosition pos) {
        if (positionIsOnGrid(pos)) {
            return this.grid.get(pos.row()).get(pos.col());
        } else {
            throw new IndexOutOfBoundsException("Grid access out of bounds at " + pos);
        }
    }

    /**
     * Checks if the specified position is within the bounds of the grid.
     *
     * @param pos the position to check
     * @return true if the position is within the bounds of the grid, false
     *         otherwise
     */
    @Override
    public boolean positionIsOnGrid(CellPosition pos) {
        if ((pos.row() < 0) || pos.row() >= this.rows) {
            return false;
        }
        if (pos.col() < 0 || pos.col() >= this.cols) {
            return false;
        }
        return true;
    }
}