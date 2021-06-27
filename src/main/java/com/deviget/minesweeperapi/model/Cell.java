package com.deviget.minesweeperapi.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Cell {

    private CellStatus status = CellStatus.COVERED;
    // 0 = not a mine; 1 = mine;
    private boolean isMine = false;
    // could be from 0 to 8
    private int numOfAdjacentMines = 0;

    public Cell(boolean isMine) {
        this.isMine = isMine;
    }

    public Cell(int numOfAdjacentMines) {
        this.numOfAdjacentMines = numOfAdjacentMines;
    }

}
