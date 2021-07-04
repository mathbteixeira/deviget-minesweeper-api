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

    public static final String COVERED = "covered";
    public static final String UNCOVERED = "uncovered";
    public static final String FLAGGED = "flagged";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private long id;

    @Column
    private int rowNum;

    @Column
    private int columnNum;

    @Column
    private String status = COVERED;

    @Column
    private boolean isMine = false;

    @Column
    private int numOfAdjacentMines = 0;

    @ManyToOne
    @JoinColumn(name="game_id")
    private Game game;

    public Cell(int rowNum, int columnNum, int numOfAdjacentMines, boolean isMine) {
        this.rowNum = rowNum;
        this.columnNum = columnNum;
        this.numOfAdjacentMines = numOfAdjacentMines;
        this.isMine = isMine;
    }

}
