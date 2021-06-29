package com.deviget.minesweeperapi.vo;

import com.deviget.minesweeperapi.model.CellStatus;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import java.io.Serializable;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class GameVO implements Serializable {

    private static final long serialVersionUID = 1L;

    @JsonProperty("id")
    private Long id;

    @JsonProperty("numOfRows")
    private Integer numOfRows;

    @JsonProperty("numOfColumns")
    private Integer numOfColumns;

    @JsonProperty("numOfMines")
    private Integer numOfMines;

    @JsonProperty("status")
    private String status;

    @JsonProperty("cells")
    private List<CellVO> cells;
}
