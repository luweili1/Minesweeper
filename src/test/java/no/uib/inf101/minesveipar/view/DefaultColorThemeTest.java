package no.uib.inf101.minesveipar.view;

import static org.junit.jupiter.api.Assertions.assertEquals;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.awt.Color;

import no.uib.inf101.minesveipar.model.MineCell;
import org.junit.jupiter.api.Test;

public class DefaultColorThemeTest {

    @Test
    public void testDefaultColorTheme() {
        ColorTheme colorTheme = new DefaultColorTheme();

        assertEquals(Color.BLUE, colorTheme.getCellColor(new MineCell(1, false)));
        assertEquals(Color.GREEN, colorTheme.getCellColor(new MineCell(2, false)));
        assertEquals(Color.RED, colorTheme.getCellColor(new MineCell(3, false)));
        assertEquals(Color.ORANGE, colorTheme.getCellColor(new MineCell(4, false)));
        assertEquals(Color.CYAN, colorTheme.getCellColor(new MineCell(5, false)));
        assertEquals(Color.MAGENTA, colorTheme.getCellColor(new MineCell(6, false)));
        assertEquals(Color.YELLOW, colorTheme.getCellColor(new MineCell(7, false)));
        assertEquals(Color.PINK, colorTheme.getCellColor(new MineCell(8, false)));
        assertEquals(Color.GRAY, colorTheme.getCellColor(new MineCell(0, false)));
        assertEquals(Color.BLACK, colorTheme.getCellColor(new MineCell(-1, false)));
        assertEquals(Color.GRAY, colorTheme.getCellColor(new MineCell(10, false)));

        assertThrows(IllegalArgumentException.class, () -> colorTheme.getCellColor(new MineCell(9, false)));
    }

    @Test
    public void testFrameColor() {
        ColorTheme colorTheme = new DefaultColorTheme();
        assertEquals(Color.WHITE, colorTheme.getFrameColor());
    }

    @Test
    public void testBackgroundColor() {
        ColorTheme colorTheme = new DefaultColorTheme();
        assertEquals(new Color(220, 220, 220), colorTheme.getBackgroundColor());
    }

    @Test
    public void testGameOverColor() {
        ColorTheme colorTheme = new DefaultColorTheme();
        assertEquals(new Color(0, 0, 0, 128), colorTheme.getGameOverColor());
    }

    @Test
    public void testGameOverTextColor() {
        ColorTheme colorTheme = new DefaultColorTheme();
        assertEquals(Color.RED, colorTheme.getGameOverTextColor());
    }

    @Test
    public void testUncoveredCellColor() {
        ColorTheme colorTheme = new DefaultColorTheme();
        assertEquals(Color.LIGHT_GRAY, colorTheme.getUncoveredCellColor());
    }

    @Test
    public void testGameWonColor() {
        ColorTheme colorTheme = new DefaultColorTheme();
        assertEquals(new Color(0, 0, 0, 128), colorTheme.getGameWonColor());
    }
}
