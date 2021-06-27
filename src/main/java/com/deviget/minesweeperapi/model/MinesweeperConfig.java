package com.deviget.minesweeperapi.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class MinesweeperConfig {

    private int rows;
    private int columns;
    private int numOfMines;
    private Cell[][] configArray;

    public MinesweeperConfig(int rows, int columns, int numOfMines) {
        this.rows = rows;
        this.columns = columns;
        this.numOfMines = numOfMines;
    }
}
