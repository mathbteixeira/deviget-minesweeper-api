package com.deviget.minesweeperapi.model;

import lombok.Getter;
import lombok.Setter;

public enum CellStatus {
    UNCOVERED("uncovered"),
    COVERED("covered"),
    FLAGGED("flagged");

    @Getter
    @Setter
    private String description;

    CellStatus(String description) {
        this.description = description;
    }
}
