package no.uib.inf101.minesveipar.controller;

import no.uib.inf101.grid.CellPosition;
import no.uib.inf101.grid.GridDimension;
import no.uib.inf101.minesveipar.model.GameState;

public interface ControllableMineSveiparModel {
    void placingMines();

    Integer uncoverCell(CellPosition pos);

    void uncoverSurroundingCells(CellPosition pos);

    GridDimension getDimension();

    void flagMine(CellPosition pos);

    boolean isFlagged(CellPosition pos);

    GameState getGameState();

}