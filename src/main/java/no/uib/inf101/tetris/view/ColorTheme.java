package no.uib.inf101.tetris.view;

import java.awt.Color;

public interface ColorTheme {
    
    public Color getCellColor(Character c);
    
    public Color getFrameColor();

    public Color getBackgroundColor();

}
