package no.uib.inf101.minesveipar.view;

import java.awt.Color;

import no.uib.inf101.minesveipar.model.MineCell;

public interface ColorTheme {

    /**
     * The color of a given character
     *
     * @param c the character to find the color of
     * @return a color
     */
    Color getCellColor(MineCell mineCell);

    /**
     * The color of the frame
     *
     * @return a color
     */
    Color getFrameColor();

    Color getGameOverColor();

    Color getGameOverTextColor();

    Color getUncoveredCellColor();

    /**
     * The color of the background
     *
     * @return background color
     */
    Color getBackgroundColor();

    Color getGameWonColor();

}
