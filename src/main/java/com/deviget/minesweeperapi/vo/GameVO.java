package com.deviget.minesweeperapi.vo;

import com.deviget.minesweeperapi.model.CellStatus;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class GameVO implements Serializable {

    private static final long serialVersionUID = 1L;

    @JsonProperty("rows")
    private Integer rows;

    @JsonProperty("columns")
    private Integer columns;

    @JsonProperty("mines")
    private Integer mines;
}
