package no.uib.inf101.tetris.view;

import java.awt.Color;

public class DefaultColorTheme implements ColorTheme {

    @Override
    public Color getCellColor(Character c) {
        Color color = switch (c) {
            case 'L' -> Color.RED;
            case 'J' -> Color.GREEN;
            case 'S' -> Color.YELLOW;
            case 'Z' -> Color.CYAN;
			case 'T' -> Color.WHITE;
			case 'I' -> Color.MAGENTA;
			case 'O' -> Color.BLUE;
			
            case '-' -> Color.BLACK;
            default -> throw new IllegalArgumentException(
                    "No available color for '" + c + "'");
        };
        return color;
    }

    @Override
    public Color getFrameColor() {
        return new Color(0, 0, 0, 0);
    }

    @Override
    public Color getBackgroundColor() {
        return null;
    }

}
