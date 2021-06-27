package com.deviget.minesweeperapi.service;

import com.deviget.minesweeperapi.model.Cell;
import com.deviget.minesweeperapi.model.MinesweeperConfig;
import org.springframework.stereotype.Service;

import java.util.Random;
import java.util.logging.Logger;

@Service
public class MinesweeperConfigService {

    private static final Logger logger = Logger.getLogger("MinesweeperConfig");

    private MinesweeperConfig config;

    public void configureGame(Integer rows, Integer columns, Integer mines) {
        config = new MinesweeperConfig(rows, columns, mines);
        //start array of game with rows and cols
        config.setConfigArray(new Cell[config.getRows()][config.getColumns()]);
        //generate Random posittion of the mines
        configureMines(config.getConfigArray(), config.getNumOfMines());
        //calculate numbers
        calculateNumbers();
    }

    public void configureMines(Cell[][] configArray, int numOfMines) {
        int counter = 0;
        Random random = new Random();
        while (counter < numOfMines) {
            int y = random.nextInt(config.getColumns());
            int x = random.nextInt(config.getRows());
            if (configArray[x][y] == null || !configArray[x][y].isMine()) {
                configArray[x][y] = new Cell(true);
                counter++;
            }
        }
    }

    public void calculateNumbers() {
        for (int r = 0; r < config.getRows(); r++) {
            for (int c = 0; c < config.getColumns(); c++) {
                if (config.getConfigArray()[r][c] == null || !config.getConfigArray()[r][c].isMine()) {
                    config.getConfigArray()[r][c] = new Cell(calculateNumberOfAdjacentMines(c, r));
                }
            }
        }
        printConfigArray();
    }

    public int calculateNumberOfAdjacentMines(int column, int row) {
        int counter = 0;
        for (int r = row - 1; r <= row + 1; r++) {
            for (int c = column - 1; c <= column + 1; c++) {
                if ((c >= 0 && c < config.getColumns())
                        && (r >= 0 && r < config.getRows())
                        && (config.getConfigArray()[r][c] != null && config.getConfigArray()[r][c].isMine())) {
                    counter++;
                }
            }
        }
        return counter;
    }

    public void printConfigArray() {
        for (int row = 0; row < config.getConfigArray().length; row++) {
            for (int col = 0; col < config.getConfigArray()[row].length; col++) {
                System.out.printf("%s ", config.getConfigArray()[row][col].isMine() ? "B" : config.getConfigArray()[row][col].getNumOfAdjacentMines());
            }
            System.out.println(); //Makes a new row
        }
    }
}
