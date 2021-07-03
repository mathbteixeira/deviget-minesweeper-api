package com.deviget.minesweeperapi.vo;

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

    @JsonProperty("id")
    private Long id;

    @JsonProperty("rowNum")
    private Integer rowNum;

    @JsonProperty("columnNum")
    private Integer columnNum;

    @JsonProperty("status")
    private String status;

    @JsonProperty("mine")
    private String mine;

    @JsonProperty("isMine")
    private Integer numOfAdjacentMines;

    @JsonProperty("gameId")
    private Long gameId;

}
