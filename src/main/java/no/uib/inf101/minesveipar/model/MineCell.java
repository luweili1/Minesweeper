package no.uib.inf101.minesveipar.model;

public class MineCell {
    int value;
    boolean hidden;

    public MineCell(int value, boolean hidden) {
        this.value = value;
        this.hidden = hidden;

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