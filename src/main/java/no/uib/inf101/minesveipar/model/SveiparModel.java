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

/**
 * The `SveiparModel` class represents the model component of the MineSveipar
 * game.
 * It implements both the `ViewableMineSveiparModel` and
 * `ControllableMineSveiparModel` interfaces.
 * The class is responsible for managing the game board, game state, and game
 * logic.
 */
public class SveiparModel implements ViewableMineSveiparModel, ControllableMineSveiparModel {

    Board board;
    public GameState gameState;
    public List<GridCell<Integer>> flaggedValues;
    public final int NUM_MINES = 8;
    public static final int MINE_VALUE = -1;
    public static final int WRONG_FLAG = 10;
    public static final int CORRECT_FLAG = -10;
    int value;

    public SveiparModel(Board board) {
        this.board = board;
        this.gameState = GameState.ACTIVE_GAME;
        this.flaggedValues = new ArrayList<>();
        gameState = GameState.WELCOME_SCREEN;

        placingMines();
        countSurroundingMines();
        updateOpenedCellValues();
    }

    @Override
    public GridDimension getDimension() {
        return this.board;
    }

    @Override
    public GameState getGameState() {
        return gameState;
    }

    @Override
    public void setGameState(GameState gameState) {
        this.gameState = gameState;
    }

    @Override
    public void placingMines() {
        Random random = new Random();
        int placedMines = 0;
        while (placedMines < NUM_MINES) {
            int a = random.nextInt(this.board.rows());
            int b = random.nextInt(this.board.cols());
            if (canPlaceMines(new CellPosition(a, b))) {
                this.board.set(new CellPosition(a, b), new MineCell(MINE_VALUE, true));
                placedMines++;
            }
        }
    }

    /**
     * Counts the number of surrounding mines for each cell on the board.
     * Updates the value of each non-mine cell with the count of surrounding mines.
     */
    private void countSurroundingMines() {
        for (int i = 0; i < this.board.rows(); i++) {
            for (int j = 0; j < this.board.cols(); j++) {
                CellPosition pos = new CellPosition(i, j);
                MineCell cell = this.board.get(pos);
                if (cell.getValue() != MINE_VALUE) {
                    int count = 0;
                    for (int dx = -1; dx <= 1; dx++) {
                        for (int dy = -1; dy <= 1; dy++) {
                            if (dx == 0 && dy == 0)
                                continue;
                            CellPosition neighborPos = new CellPosition(pos.row() + dx, pos.col() + dy);
                            if (this.board.positionIsOnGrid(neighborPos)) {
                                MineCell neighborCell = this.board.get(neighborPos);
                                if (neighborCell.getValue() == MINE_VALUE) {
                                    count++;
                                }
                            }
                        }
                    }
                    this.board.set(pos, new MineCell(count, cell.getHidden()));
                }
            }
        }
    }

    /**
     * Updates the values of opened cells on the board.
     * For each opened cell, the adjacent mine count is calculated and set as the
     * value of the cell.
     * If the cell does not contain a mine, it is marked as not a mine.
     */
    private void updateOpenedCellValues() {
        for (int i = 0; i < this.board.rows(); i++) {
            for (int j = 0; j < this.board.cols(); j++) {
                CellPosition pos = new CellPosition(i, j);
                if (!isHidden(pos)) {
                    int adjacentMineCount = countAdjacentMines(pos);
                    this.board.set(pos, new MineCell(adjacentMineCount, false));
                }
            }
        }
    }

    /**
     * Uncover a cell at the specified position and update the game state
     * accordingly.
     * 
     * @param pos the position of the cell to uncover
     * @return the value of the uncovered cell, or null if the cell is already
     *         uncovered
     */
    public Integer uncoverCell(CellPosition pos) {
        if (isFlagged(pos)) {
            return null;
        }

        if (!isHidden(pos)) {
            return null;
        }

        this.value = this.board.get(pos).getValue();
        if (value == MINE_VALUE) {
            gameState = GameState.GAME_OVER;
            uncoverMines();
        } else {
            int adjacentMineCount = countAdjacentMines(pos);
            this.board.set(pos, new MineCell(adjacentMineCount, false));
            if (adjacentMineCount == 0) {
                uncoverSurroundingCells(pos);
            }
        }

        return value;
    }

    /**
     * Flags a mine at the specified position.
     * If the cell at the given position is hidden and not already flagged, and
     * there are remaining mines,
     * the cell is flagged and added to the list of flagged cells.
     * If the cell at the given position is already flagged, it is unflagged and
     * removed from the list of flagged cells.
     *
     * @param pos the position of the cell to flag/unflag
     */
    @Override
    public void flagMine(CellPosition pos) {
        if (isHidden(pos) == true) {
            if (!isFlagged(pos)) {
                if (mineCounter() != 0) {
                    this.flaggedValues.add(new GridCell<Integer>(pos, this.board.get(pos).getValue()));
                    if (this.board.get(pos).getValue() == -1) {
                        this.board.set(pos, new MineCell(WRONG_FLAG, true));
                    } else {
                        this.board.set(pos, new MineCell(CORRECT_FLAG, true));
                    }
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

    /**
     * Counts the number of adjacent cells that contain mines.
     *
     * @param pos The position of the cell to check for adjacent mines.
     * @return The number of adjacent cells that contain mines.
     */
    private int countAdjacentMines(CellPosition pos) {
        int mineCount = 0;
        for (int dx = -1; dx <= 1; dx++) {
            for (int dy = -1; dy <= 1; dy++) {
                if (dx == 0 && dy == 0)
                    continue;
                CellPosition neighborPos = new CellPosition(pos.row() + dx, pos.col() + dy);
                if (this.board.positionIsOnGrid(neighborPos) && isMine(neighborPos)) {
                    mineCount++;
                }
            }
        }
        return mineCount;
    }

    /**
     * Checks if the given cell position is valid and empty.
     *
     * @param pos the cell position to check
     * @return true if the cell position is valid and empty, false otherwise
     */

    public void uncoverSurroundingCells(CellPosition pos) {
        int[][] directions = {
                { -1, 0 }, { 1, 0 }, { 0, -1 }, { 0, 1 },
                { -1, -1 }, { -1, 1 }, { 1, -1 }, { 1, 1 }
        };

        for (int[] dir : directions) {
            int newRow = pos.row() + dir[0];
            int newCol = pos.col() + dir[1];
            CellPosition neighborPos = new CellPosition(newRow, newCol);

            if (this.board.positionIsOnGrid(neighborPos) && isHidden(neighborPos) && !isFlagged(neighborPos)) {
                uncoverCell(neighborPos);
            }
        }
    }

    /**
     * Performs a breadth-first search to uncover all empty cells starting from the
     * given position.
     * This method uses a queue to keep track of the cells to be processed.
     *
     * @param startPos The starting position for the search.
     */
    public void uncoverEmptyCellsBFS(CellPosition startPos) {
        List<CellPosition> queue = new ArrayList<>();
        queue.add(startPos);
        while (!queue.isEmpty()) {
            CellPosition currentPos = queue.remove(0);
            for (int dx = -1; dx <= 1; dx++) {
                for (int dy = -1; dy <= 1; dy++) {
                    CellPosition neighborPos = new CellPosition(currentPos.row() + dx, currentPos.col() + dy);
                    if (this.board.positionIsOnGrid(neighborPos) && this.board.get(neighborPos).getValue() == 0) {
                        queue.add(neighborPos);
                        uncoverCell(neighborPos);
                    }
                }
            }
        }
    }

    /**
     * Resets the game by clearing the board, flagged values, and setting the game
     * state to active.
     * It also counts the surrounding mines and places new mines on the board.
     */
    public void resetGame() {
        this.board.clear();
        this.flaggedValues.clear();
        this.gameState = GameState.ACTIVE_GAME;
        countSurroundingMines();
        placingMines();

    }

    /**
     * Uncover all mines on the game board.
     * This method iterates through each cell on the board and checks if it contains
     * a mine.
     * If a cell contains a mine, it sets the cell as uncovered.
     */
    private void uncoverMines() {
        for (int i = 0; i < this.board.rows(); i++) {
            for (int j = 0; j < this.board.cols(); j++) {
                if (this.board.get(new CellPosition(i, j)).getValue() == -1) {
                    setUncovered(new CellPosition(i, j), false);
                }
            }
        }
    }

    /**
     * Checks if the cell at the given position is a mine.
     *
     * @param pos the position of the cell to check
     * @return true if the cell is a mine, false otherwise
     */
    private boolean isMine(CellPosition pos) {
        return this.board.get(pos).getValue() == MINE_VALUE || this.board.get(pos).getValue() == WRONG_FLAG;
    }

    /**
     * Checks if a mine can be placed at the given cell position.
     *
     * @param pos the cell position to check
     * @return {@code true} if a mine can be placed, {@code false} otherwise
     */
    private Boolean canPlaceMines(CellPosition pos) {
        if (this.board.get(pos).getValue() == -1) {
            return false;
        }
        return true;
    }

    /**
     * Sets the uncovered state of a cell at the specified position.
     * If the cell is not hidden, it updates the cell on the board to be a MineCell
     * with the same value and false hidden state.
     *
     * @param pos    the position of the cell to set the uncovered state for
     * @param hidden the hidden state of the cell
     */

    private void setUncovered(CellPosition pos, boolean hidden) {
        if (hidden == false) {
            this.board.set(pos, new MineCell(this.board.get(pos).getValue(), false));
        }
    }

    @Override
    public boolean isFlagged(CellPosition pos) {
        if ((this.board.get(pos).getValue()) == WRONG_FLAG || (this.board.get(pos).getValue()) == CORRECT_FLAG) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * Checks if the cell at the specified position is hidden.
     *
     * @param pos the position of the cell to check
     * @return true if the cell is hidden, false otherwise
     */
    private boolean isHidden(CellPosition pos) {
        boolean value = this.board.get(pos).getHidden();
        if (value == true) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public int mineCounter() {
        int counter = NUM_MINES;
        int mineCount = 0;
        for (GridCell<Integer> cell : flaggedValues) {
            if (isFlagged(cell.pos())) {
                counter--;
                if (cell.value() == -1) {
                    mineCount++;
                }
            }
            if (mineCount == NUM_MINES) {
                gameState = GameState.GAME_WON;
            }
        }
        return counter;
    }

    @Override
    public int value(CellPosition pos) {
        return this.board.get(pos).getValue();
    }

    @Override
    public Iterable<GridCell<MineCell>> getTilesOnBoard() {
        List<GridCell<MineCell>> result = new ArrayList<>();

        for (int i = 0; i < this.board.rows(); i++) {
            for (int j = 0; j < this.board.cols(); j++) {
                GridCell<MineCell> cell = new GridCell<MineCell>(new CellPosition(i, j),
                        this.board.get(new CellPosition(i, j)));
                result.add(cell);
            }
        }
        return result;
    }
}