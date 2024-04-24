package no.uib.inf101.minesveipar.view;

import java.awt.Color;

import no.uib.inf101.minesveipar.model.MineCell;

public interface ColorTheme {

    /**
     * Returns the color of a given mine cell.
     *
     * @param mineCell the mine cell to find the color of
     * @return the color of the mine cell
     */
    Color getCellColor(MineCell mineCell);

    /**
     * Returns the color of the frame.
     *
     * @return the color of the frame
     */
    Color getFrameColor();

    /**
     * Returns the color of the "game over" message.
     *
     * @return the color of the "game over" message
     */
    Color getGameOverColor();

    /**
     * Returns the color of the text in the "game over" message.
     *
     * @return the color of the text in the "game over" message
     */
    Color getGameOverTextColor();

    /**
     * Returns the color of an uncovered cell.
     *
     * @return the color of an uncovered cell
     */
    Color getUncoveredCellColor();

    /**
     * Returns the background color.
     *
     * @return the background color
     */
    Color getBackgroundColor();

    /**
     * Returns the color of the "game won" message.
     *
     * @return the color of the "game won" message
     */
    Color getGameWonColor();

}
