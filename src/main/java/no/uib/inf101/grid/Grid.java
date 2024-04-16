package no.uib.inf101.grid;

import java.util.ArrayList;
import java.util.Iterator;

public class Grid<E> implements IGrid<E> {
    int rows;
    int cols;

    private ArrayList<ArrayList<E>> grid;

    public Grid(int rows, int cols) {
        // konstruktør 1
        this(rows, cols, null);

    }

    public Grid(int rows, int cols, E defaultvalue) {
        // konstruktør 2
        this.grid = new ArrayList<>();
        this.rows = rows;
        this.cols = cols;
        for (int i = 0; i < rows; i++) {
            this.grid.add(new ArrayList<>());
            for (int j = 0; j < cols; j++) {
                this.grid.get(i).add(defaultvalue);
            }
        }

    }

    @Override
    public int rows() {
        return this.rows;
    }

    @Override
    public int cols() {
        return this.cols;
    }

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

    @Override
    public void set(CellPosition pos, E value) {
        if (pos.row() >= 0 && pos.row() < this.rows() && pos.col() >= 0 && pos.col() < this.cols()) {
            this.grid.get(pos.row()).set(pos.col(), value);
        } else {
            throw new IndexOutOfBoundsException();
        }
    }

    @Override
    public E get(CellPosition pos) {
        if (pos.row() >= 0 && pos.row() < this.rows() && pos.col() >= 0 && pos.col() < this.cols()) {
            return this.grid.get(pos.row()).get(pos.col());
        } else {
            throw new IndexOutOfBoundsException();
        }
    }

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