package com.deviget.minesweeperapi.service;

import com.deviget.minesweeperapi.model.Cell;
//import com.deviget.minesweeperapi.model.MinesweeperConfig;
import com.deviget.minesweeperapi.model.CellStatus;
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
import java.util.logging.Logger;
import java.util.stream.Collectors;

@Service
public class MinesweeperConfigService {

    private static final Logger logger = Logger.getLogger("MinesweeperConfig");

    @Autowired
    GameRepository gameRepository;

    @Autowired
    CellRepository cellRepository;

    //private MinesweeperConfig config;
    @Getter
    @Setter
    Cell[][] configArray;

    public void configureGame(GameVO gameVo) {
        Game game = new Game(gameVo.getRows(), gameVo.getColumns(), gameVo.getMines());
        //start array of game with rows and columns
        setConfigArray(new Cell[gameVo.getRows()][gameVo.getColumns()]);
        //generate Random position of the mines
        configureMines(gameVo.getMines(), gameVo.getRows(), gameVo.getColumns());
        //calculate numbers
        calculateNumbers(gameVo.getRows(), gameVo.getColumns());
        Game gameSaved = gameRepository.save(game);
        List<Cell> savedCells = saveCells(game);
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

    public String revealCell(Long id) {
        Cell cell = cellRepository.getById(id);
        if (!cell.getGame().getStatus().equals(Game.CLOSED)) {
            if (cell.isMine()) {
                return endGame(cell.getGame());
            } else {
                cell.setStatus(Cell.UNCOVERED);
                cellRepository.save(cell);
                return "Cell uncovered.";
            }
        }
        else {
            return "Game " + cell.getGame().getId() + " is closed.";
        }
    }

    public String flagCell(Long id) {
        Cell cell = cellRepository.getById(id);
        if (!cell.getGame().getStatus().equals(Game.CLOSED)) {
            cell.setStatus(Cell.FLAGGED);
            cellRepository.save(cell);
            return "Cell flagged.";
        }
        else {
            return "Game " + cell.getGame().getId() + " is closed.";
        }
    }

    private String endGame(Game game) {
        game.setStatus(Game.CLOSED);
        gameRepository.save(game);
        return "Boom! You hit a bomb!";
    }

    public List<CellVO> getCells(Long gameId) {
        List<Cell> cellList = cellRepository.findAll().stream().filter(cell ->
                cell.getGame().getId() == gameId).collect(Collectors.toList());
        List<CellVO> cellVoList = new ArrayList<>();
        cellList.forEach(cell -> {
            CellVO cellVo = new CellVO();
            cellVo.setId(cell.getId());
            cellVo.setRowNum(cell.getRowNum());
            cellVo.setColumnNum(cell.getColumnNum());
            cellVo.setStatus(cell.getStatus());
            cellVo.setMine(cell.isMine() ? "Mine" : "Not a Mine");
            cellVo.setNumOfAdjacentMines(cell.getNumOfAdjacentMines());
            cellVo.setGameId(cell.getGame().getId());
            cellVoList.add(cellVo);
        });
        return cellVoList;
    }
}
