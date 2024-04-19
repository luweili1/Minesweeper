package no.uib.inf101.minesveipar.view;

import java.awt.Color;

import no.uib.inf101.grid.Grid;
import no.uib.inf101.minesveipar.model.MineCell;
import no.uib.inf101.minesveipar.model.SveiparModel;

public class DefaultColorTheme implements ColorTheme {
    SveiparModel model;
    Grid<Integer> grid;

    @Override
    public Color getCellColor(MineCell mineCell) {
        Color color = switch (mineCell.getValue()) {
            case 1 -> Color.BLUE;
            case 2 -> Color.GREEN;
            case 3 -> Color.RED;
            case 4 -> Color.ORANGE;
            case 5 -> Color.CYAN;
            case 6 -> Color.MAGENTA;
            case 7 -> Color.YELLOW;
            case 8 -> Color.PINK;
            case 0 -> Color.GRAY;
            case -1 -> Color.BLACK;
            case 10 -> Color.GRAY;
            default -> throw new IllegalArgumentException(
                    "No available color for '" + mineCell.getValue() + "'");
        };
        return color;
    }

    @Override
    public Color getFrameColor() {
        return new Color(91, 91, 91);
    }

    @Override
    public Color getBackgroundColor() {
        return new Color(220, 220, 220);
    }

    @Override
    public Color getGameOverColor() {
        return new Color(0, 0, 0, 128);

    }

    @Override
    public Color getGameOverTextColor() {
        return Color.RED;
    }

    @Override
    public Color getUncoveredCellColor() {
        return new Color(238, 238, 238);
    }

    @Override
    public Color getGameWonColor() {
        return new Color(0, 0, 0, 128);

    }

}
