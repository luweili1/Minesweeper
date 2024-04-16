package no.uib.inf101.minesveipar.view;

import no.uib.inf101.grid.CellPosition;
import no.uib.inf101.grid.GridCell;
import no.uib.inf101.grid.GridDimension;
import no.uib.inf101.minesveipar.model.GameState;
import no.uib.inf101.minesveipar.model.MineCell;

public interface ViewableMineSveiparModel {

    GridDimension getDimension();

    Iterable<GridCell<MineCell>> getTilesonBoard();

    GameState getGameState();

    Integer uncoverCell(CellPosition pos);

    void uncoverSurroundingCells(CellPosition pos);

    void flagMine(CellPosition pos);

    int mineCounter();

}
