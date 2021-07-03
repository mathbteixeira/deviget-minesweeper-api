package com.deviget.minesweeperapi.service;

import com.deviget.minesweeperapi.converter.CellConverter;
import com.deviget.minesweeperapi.converter.GameConverter;
import com.deviget.minesweeperapi.model.Cell;
import com.deviget.minesweeperapi.model.Game;
import com.deviget.minesweeperapi.repository.CellRepository;
import com.deviget.minesweeperapi.repository.GameRepository;
import com.deviget.minesweeperapi.vo.CellVO;
import com.deviget.minesweeperapi.vo.GameVO;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

@Service
public class MinesweeperConfigService {

    @Autowired
    GameRepository gameRepository;

    @Autowired
    CellRepository cellRepository;

    @Getter
    @Setter
    Cell[][] configArray;

    public GameVO configureGame(GameVO gameVo) {
        Game game = new Game(gameVo.getNumOfRows(), gameVo.getNumOfColumns(), gameVo.getNumOfMines());
        //start array of game with rows and columns
        setConfigArray(new Cell[gameVo.getNumOfRows()][gameVo.getNumOfColumns()]);
        //generate Random position of the mines
        configureMines(gameVo.getNumOfMines(), gameVo.getNumOfRows(), gameVo.getNumOfColumns());
        //calculate numbers of each cell
        calculateNumbers(gameVo.getNumOfRows(), gameVo.getNumOfColumns());
        Game gameSaved = gameRepository.save(game);
        List<Cell> savedCells = saveCells(game);
        game.setCells(savedCells);
        return GameConverter.convertToGameVO(gameSaved);
    }

    private List<Cell> saveCells(Game game) {
        List<Cell> savedCells = new ArrayList<Cell>();
        for (Cell[] cellArray : configArray) {
            Arrays.stream(cellArray).forEach(c -> {
                c.setGame(game);
                savedCells.add(cellRepository.save(c));
            });
        }
        return savedCells;
    }

    public void configureMines(int numOfMines, int rows, int columns) {
        int counter = 0;
        Random random = new Random();
        while (counter < numOfMines) {
            int x = random.nextInt(rows);
            int y = random.nextInt(columns);
            if (getConfigArray()[x][y] == null || !getConfigArray()[x][y].isMine()) {
                getConfigArray()[x][y] = new Cell(x, y, 0, true);
                counter++;
            }
        }
    }

    public void calculateNumbers(int rows, int columns) {
        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < columns; c++) {
                if (getConfigArray()[r][c] == null || !getConfigArray()[r][c].isMine()) {
                    getConfigArray()[r][c] = new Cell(r, c, calculateNumberOfAdjacentMines(c, r, rows, columns), false);
                }
            }
        }
        printConfigArray();
    }

    public int calculateNumberOfAdjacentMines(int column, int row, int rows, int columns) {
        int counter = 0;
        for (int r = row - 1; r <= row + 1; r++) {
            for (int c = column - 1; c <= column + 1; c++) {
                if ((c >= 0 && c < columns)
                        && (r >= 0 && r < rows)
                        && (getConfigArray()[r][c] != null && getConfigArray()[r][c].isMine())) {
                    counter++;
                }
            }
        }
        return counter;
    }

    public void printConfigArray() {
        for (int row = 0; row < getConfigArray().length; row++) {
            for (int col = 0; col < getConfigArray()[row].length; col++) {
                System.out.printf("%s ", getConfigArray()[row][col].isMine() ? "B" : getConfigArray()[row][col].getNumOfAdjacentMines());
            }
            System.out.println(); //Makes a new row
        }
    }

    public CellVO revealCell(Long id) {
        Cell cell = cellRepository.getById(id);
        if (!cell.getGame().getStatus().equals(Game.CLOSED)) {
            if (cell.isMine()) {
                return endGame(cell.getGame());
            } else {
                if (!cell.getStatus().equals(Cell.UNCOVERED)) {
                    cell = uncoverCell(cell);
                    if (cell.getNumOfAdjacentMines() == 0) {
                        revealAdjacentCells(cell);
                    }
                    return CellConverter.convertToCellVO(cell);
                }
                else {
                    return null;
                }
            }
        }
        else {
            return null;
        }
    }

    private void revealAdjacentCells(Cell cell) {
        List<Cell> cellsWithNoAdjacentMines = new ArrayList<>();
        for (int row = cell.getRowNum() - 1; row <= cell.getRowNum() + 1; row++) {
            for (int col = cell.getColumnNum() - 1; col <= cell.getColumnNum() + 1; col++) {
                if ((col >= 0 && col < cell.getGame().getNumOfColumns())
                        && (row >= 0 && row < cell.getGame().getNumOfRows())
                        && !(row == cell.getRowNum() && col == cell.getColumnNum())) {
                    Cell adjacentCell = cellRepository.findByGameIdAndRowAndColumn(cell.getGame().getId(), row, col);
                    if (!adjacentCell.isMine() && !adjacentCell.getStatus().equals(Cell.UNCOVERED)) {
                        adjacentCell = uncoverCell(adjacentCell);
                        if (adjacentCell.getNumOfAdjacentMines() == 0) {
                            cellsWithNoAdjacentMines.add(adjacentCell);
                        }
                    }
                }
            }
        }
        if (!cellsWithNoAdjacentMines.isEmpty()) {
            for (Cell c : cellsWithNoAdjacentMines) {
                revealAdjacentCells(c);
            }
        }
    }

    private Cell uncoverCell(Cell cell) {
        cell.setStatus(Cell.UNCOVERED);
        return cellRepository.save(cell);
    }

    public String flagCell(Long id) {
        Cell cell = cellRepository.getById(id);
        if (!cell.getGame().getStatus().equals(Game.CLOSED)) {
            switch (cell.getStatus()) {
                case Cell.COVERED:
                    cell.setStatus(Cell.FLAGGED);
                    cellRepository.save(cell);
                    return "Cell flagged.";
                case Cell.FLAGGED:
                    cell.setStatus(Cell.COVERED);
                    cellRepository.save(cell);
                    return "Cell unflagged.";
                default:
                    return "This cell is already uncovered.";
            }
        }
        else {
            return "Game " + cell.getGame().getId() + " is closed.";
        }
    }

    private CellVO endGame(Game game) {
        game.setStatus(Game.CLOSED);
        gameRepository.save(game);
        return null;
    }

    public List<CellVO> getCells(Long gameId) {
        List<Cell> cellList = cellRepository.findAll().stream().filter(cell ->
                cell.getGame().getId() == gameId).collect(Collectors.toList());
        List<CellVO> cellVoList = new ArrayList<>();
        cellList.forEach(cell -> {
            CellVO cellVo = CellConverter.convertToCellVO(cell);
            cellVoList.add(cellVo);
        });
        return cellVoList;
    }

    public GameVO getGame(Long gameId) {
        Game game = gameRepository.getById(gameId);
        GameVO gameVo = GameConverter.convertToGameVO(game);
        return gameVo;
    }

    /* TODO: Ability to start a new game and preserve/resume the old ones
       TODO: Time tracking
       TODO: Ability to support multiple users/accounts

       TODO: change revealCell method to return GameVO object
     */
}
