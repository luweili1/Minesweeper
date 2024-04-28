package no.uib.inf101.minesveipar.model;

/**
 * The MineCell class represents a cell in a minesweeper game.
 */
public class MineCell {
    private int value;
    private boolean hidden;
    private boolean flagged;

    public MineCell(int value, boolean hidden) {
        this.value = value;
        this.hidden = hidden;
    }

    public void setFlagged(boolean flagged) {
        this.flagged = flagged;
    }

    public boolean isFlagged() {
        return flagged;
    }

    public int getValue() {
        return this.value;
    }

    public boolean getHidden() {
        return this.hidden;
    }

    @Override
    public String toString() {
        if (!hidden) {
            return value == -1 ? "X" : Integer.toString(value);
        } else {
            return flagged ? "F" : "-";
        }
    }

    public boolean isMine() {
        return this.value == -1;
    }
}
