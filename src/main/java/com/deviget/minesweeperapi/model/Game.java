package com.deviget.minesweeperapi.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table
public class Game {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private int id;

    @Column
    private int numOfRows;

    @Column
    private int numOfColumns;

    @Column
    private int numOfMines;

    @OneToMany(mappedBy = "game")
    private List<Cell> cells;
}
