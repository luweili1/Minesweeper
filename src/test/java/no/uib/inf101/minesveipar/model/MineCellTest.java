package no.uib.inf101.minesveipar.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class MineCellTest {

    @Test
    public void testMineCellCreation() {
        MineCell cell = new MineCell(-1, true);

        assertEquals(-1, cell.getValue());
        assertTrue(cell.getHidden());

        cell = new MineCell(2, false);

        assertEquals(2, cell.getValue());
        assertFalse(cell.getHidden());
    }
}
