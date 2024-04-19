package no.uib.inf101.minesveipar.model;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

import no.uib.inf101.grid.CellPosition;
import no.uib.inf101.grid.GridCell;
import no.uib.inf101.grid.GridDimension;
import no.uib.inf101.minesveipar.controller.ControllableMineSveiparModel;
import no.uib.inf101.minesveipar.view.ViewableMineSveiparModel;

public class SveiparModel implements ViewableMineSveiparModel, ControllableMineSveiparModel {

    Board board;
    public GameState gameState;
    private List<GridCell<Integer>> flaggedValues;
    private final int mines = 10;
    int value;

    public SveiparModel(Board board) {
        this.board = board;
        this.gameState = GameState.ACTIVE_GAME;
        this.flaggedValues = new ArrayList<GridCell<Integer>>();
        countSurroundingMines();
        placingMines();
    }

    @Override
    public GridDimension getDimension() {
        return this.board;
    }

    private Boolean canPlaceMines(CellPosition pos) {
        if (this.board.get(pos).getValue() == -1) {
            return false;
        }
        return true;
    }

    private void countSurroundingMines() {
        for (int i = 0; i < this.board.rows(); i++) {
            for (int j = 0; j < this.board.cols(); j++) {
                CellPosition pos = new CellPosition(i, j);

                if (this.board.get(pos).getValue() != -1) {
                    int count = 0;

                    for (int rowOffset = -1; rowOffset <= 1; rowOffset++) {
                        for (int colOffset = -1; colOffset <= 1; colOffset++) {
                            if (rowOffset == 0 && colOffset == 0) {
                                continue;
                            }

                            int neighborRow = pos.row() + rowOffset;
                            int neighborCol = pos.col() + colOffset;

                            if (this.board.positionIsOnGrid(new CellPosition(neighborRow, neighborCol)) &&
                                    this.board.get(new CellPosition(neighborRow, neighborCol)).getValue() == -1) {
                                count++;
                            }
                        }
                    }

                    this.board.set(pos, new MineCell(count, true));
                }
            }
        }
    }

    @Override
    public GameState getGameState() {
        return this.gameState;
    }

    @Override
    public Integer uncoverCell(CellPosition pos) {
        if (isHidden(pos) == true) {
            value = this.board.get(pos).getValue();
            if (value != 10) {
                setUncovered(pos, false);

                if (isMine(pos)) {
                    gameState = GameState.GAME_OVER;
                    uncoverMines();
                }
                if (value == 0) {
                    uncoverSurroundingCells(pos);
                }
                return value;
            }
        }
        return null;

    }

    private boolean isValidAndEmptyCell(CellPosition pos) {
        return this.board.positionIsOnGrid(pos) && this.board.get(pos).getValue() != -1;
    }

    @Override
    public void uncoverSurroundingCells(CellPosition pos) {
        int[][] offsets = { { -1, 0 }, { -1, 1 }, { 0, 1 }, { 1, 1 }, { 1, 0 }, { 1, -1 }, { 0, -1 }, { -1, -1 } };

        for (int[] offset : offsets) {
            CellPosition neighborPos = new CellPosition(pos.row() + offset[0], pos.col() + offset[1]);
            if (isValidAndEmptyCell(neighborPos)) {
                uncoverCell(neighborPos);
            }
        }
    }

    private Boolean isMine(CellPosition pos) {
        if (this.board.get(pos).getValue() != -1) {
            return false;
        }
        return true;
    }

    private void setUncovered(CellPosition pos, boolean hidden) {
        if (hidden == false) {
            this.board.set(pos, new MineCell(this.board.get(pos).getValue(), false));
        }
    }

    boolean isHidden(CellPosition pos) {
        boolean value = this.board.get(pos).getHidden();
        if (value == true) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public void flagMine(CellPosition pos) {
        if (isHidden(pos)) {
            if (!isFlagged(pos)) {
                if (mineCounter() > 0) {
                    this.flaggedValues.add(new GridCell<Integer>(pos, this.board.get(pos).getValue()));
                    this.board.set(pos, new MineCell(10, true));
                }

            } else {
                for (Iterator<GridCell<Integer>> it = this.flaggedValues.iterator(); it.hasNext();) {
                    GridCell<Integer> cell = it.next();
                    if (cell.pos().row() == pos.row() && cell.pos().col() == pos.col()) {
                        this.board.set(pos, new MineCell(cell.value(), true));
                        it.remove();
                    }
                }
            }

        }
    }

    @Override
    public boolean isFlagged(CellPosition pos) {
        if ((this.board.get(pos).getValue()) == 10) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public int mineCounter() {
        int counter = mines;
        int mineCount = 0;
        for (GridCell<Integer> cell : flaggedValues) {
            if (isFlagged(cell.pos())) {
                counter--;
                if (cell.value() == -1) {
                    mineCount++;
                }
            }
            if (mineCount == mines) {
                gameState = GameState.GAME_WON;
            }
        }
        return counter;
    }

    private void uncoverMines() {
        for (int i = 0; i < this.board.rows(); i++) {
            for (int j = 0; j < this.board.cols(); j++) {
                if (this.board.get(new CellPosition(i, j)).getValue() == -1) {
                    setUncovered(new CellPosition(i, j), false);
                }
            }
        }
    }

    @Override
    public void placingMines() {
        Random random = new Random();
        int placedMines = 0;
        while (placedMines < mines) {
            int a = random.nextInt(this.board.rows());
            int b = random.nextInt(this.board.cols());
            if (canPlaceMines(new CellPosition(a, b))) {
                this.board.set(new CellPosition(a, b), new MineCell(-1, true));
                placedMines++;
            }
        }
    }

    @Override
    public Iterable<GridCell<MineCell>> getTilesonBoard() {
        return this.board;
    }

    @Override
    public int value() {
        return value;
    }
}