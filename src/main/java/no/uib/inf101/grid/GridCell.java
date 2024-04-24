package no.uib.inf101.grid;

/**
 * Represents a cell in a grid.
 *
 * @param <E> the type of value stored in the cell
 */
public record GridCell<E>(CellPosition pos, E value) {

    public int getValue() {
        return getValue();
    }

}
