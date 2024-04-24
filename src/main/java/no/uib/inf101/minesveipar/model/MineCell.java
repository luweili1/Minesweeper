package no.uib.inf101.minesveipar.model;

/**
 * The MineCell class represents a cell in a minesweeper game.
 */
public class MineCell {
    int value;
    boolean hidden;

    /**
     * Constructs a MineCell object with the specified value and hidden status.
     *
     * @param value  the value of the cell
     * @param hidden the hidden status of the cell
     */
    public MineCell(int value, boolean hidden) {
        this.value = value;
        this.hidden = hidden;
    }

    /**
     * Returns the value of the cell.
     *
     * @return the value of the cell
     */
    public int getValue() {
        return this.value;
    }

    /**
     * Returns the hidden status of the cell.
     *
     * @return the hidden status of the cell
     */
    public boolean getHidden() {
        return this.hidden;
    }

    /**
     * Returns a string representation of the cell.
     * If the cell is not hidden, it returns "X" if the value is -1, otherwise it
     * returns the value as a string.
     * If the cell is hidden, it returns "-".
     *
     * @return a string representation of the cell
     */
    @Override
    public String toString() {
        if (!hidden) {
            if (value == -1) {
                return "X";
            } else {
                return Integer.toString(value);
            }
        } else {
            return "-";
        }
    }
}
