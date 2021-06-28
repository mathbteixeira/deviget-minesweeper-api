package com.deviget.minesweeperapi.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table
public class Cell {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private int id;

    @Column
    private int rowNum;

    @Column
    private int columnNum;

    @Column
    private CellStatus status = CellStatus.COVERED;

    @Column
    // 0 = not a mine; 1 = mine;
    private boolean isMine = false;

    @Column
    // could be from 0 to 8
    private int numOfAdjacentMines = 0;

    @ManyToOne
    @JoinColumn(name="game_id")
    private Game game;

    public Cell(boolean isMine) {
        this.isMine = isMine;
    }

    public Cell(int numOfAdjacentMines) {
        this.numOfAdjacentMines = numOfAdjacentMines;
    }

}
