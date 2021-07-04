package com.deviget.minesweeperapi.service;

import com.deviget.minesweeperapi.converter.GameConverter;
import com.deviget.minesweeperapi.model.Cell;
import com.deviget.minesweeperapi.model.Game;
import com.deviget.minesweeperapi.repository.GameRepository;
import com.deviget.minesweeperapi.vo.CellVO;
import com.deviget.minesweeperapi.vo.GameVO;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Random;

@Service
public class GameService {

    @Autowired
    GameRepository gameRepository;

    @Autowired
    CellService cellService;

    @Getter
    @Setter
    Cell[][] configArray;

    public GameVO createGame(GameVO gameVo) {
        Game game;
        if (isGameValid(gameVo)) {
            game = new Game(gameVo.getNumOfRows(), gameVo.getNumOfColumns(), gameVo.getNumOfMines());
        }
        else {
            throw new IllegalArgumentException("Invalid game params.");
        }
        setConfigArray(new Cell[gameVo.getNumOfRows()][gameVo.getNumOfColumns()]);
        configureMines(gameVo.getNumOfMines(), gameVo.getNumOfRows(), gameVo.getNumOfColumns());
        calculateNumbers(gameVo.getNumOfRows(), gameVo.getNumOfColumns());
        Game gameSaved = gameRepository.save(game);
        List<Cell> savedCells = cellService.saveCells(game, configArray);
        game.setCells(savedCells);
        return GameConverter.convertToGameVO(gameSaved);
    }

    private boolean isGameValid(GameVO gameVo) {
        return gameVo != null
                && gameVo.getNumOfRows() != null
                && gameVo.getNumOfColumns() != null
                && gameVo.getNumOfMines() != null
                && (gameVo.getNumOfMines() < gameVo.getNumOfRows() * gameVo.getNumOfColumns());
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

    Game endGame(Game game) {
        game.setStatus(Game.CLOSED);
        return gameRepository.save(game);
    }

    public GameVO getGame(Long gameId) {
        Game game = gameRepository.getById(gameId);
        GameVO gameVo = GameConverter.convertToGameVO(game);
        return gameVo;
    }

    public GameVO pauseGame(Long gameId) {
        Game game = gameRepository.getById(gameId);
        switch (game.getStatus()) {
            case Game.CLOSED:
                // game closed
                return null;
            case Game.OPEN:
                game.setStatus(Game.PAUSED);
                return GameConverter.convertToGameVO(gameRepository.save(game));
            case Game.PAUSED:
                // game already paused
                return GameConverter.convertToGameVO(game);
            default:
                return null;
        }
    }

    public GameVO resumeGame(Long gameId) {
        Game game = gameRepository.getById(gameId);
        switch (game.getStatus()) {
            case Game.CLOSED:
                // game closed
                return null;
            case Game.PAUSED:
                game.setStatus(Game.OPEN);
                return GameConverter.convertToGameVO(gameRepository.save(game));
            case Game.OPEN:
                // game already open
                return GameConverter.convertToGameVO(game);
            default:
                return null;
        }
    }

    /* TODO: Time tracking
       TODO: Ability to support multiple users/accounts

       TODO: change revealCell method to return GameVO object
       TODO: add validation for game paused
     */
}
