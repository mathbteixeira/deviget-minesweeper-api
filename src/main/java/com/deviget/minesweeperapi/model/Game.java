package com.deviget.minesweeperapi.model;

import com.deviget.minesweeperapi.converter.CellConverter;
import com.deviget.minesweeperapi.vo.CellVO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table
public class Game {

    public static final String OPEN = "open";
    public static final String CLOSED = "closed";
    public static final String PAUSED = "paused";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private long id;

    @Column
    private int numOfRows;

    @Column
    private int numOfColumns;

    @Column
    private int numOfMines;

    @Column
    private String status = OPEN;

    @OneToMany(mappedBy = "game")
    private List<Cell> cells;

    public Game(int numOfRows, int numOfColumns, int numOfMines) {
        this.numOfRows = numOfRows;
        this.numOfColumns = numOfColumns;
        this.numOfMines = numOfMines;
    }

    public List<CellVO> getCellVoList() {
        if (this.getCells() != null && !this.getCells().isEmpty()) {
            List<CellVO> cellVoList = new ArrayList<>();
            this.getCells().forEach(cell -> {
                CellVO cellVo = CellConverter.convertToCellVO(cell);
                cellVoList.add(cellVo);
            });
            return cellVoList;
        }
        else {
            return null;
        }
    }
}
