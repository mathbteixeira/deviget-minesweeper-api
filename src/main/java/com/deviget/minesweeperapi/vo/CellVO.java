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
public class CellVO implements Serializable {

    private static final long serialVersionUID = 1L;

    @JsonProperty("row")
    private Integer row;

    @JsonProperty("column")
    private Integer column;

    @JsonProperty("status")
    private CellStatus status;
}
