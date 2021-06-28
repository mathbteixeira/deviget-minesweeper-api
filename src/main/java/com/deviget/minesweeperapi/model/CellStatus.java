package com.deviget.minesweeperapi.model;

import com.deviget.minesweeperapi.exception.UnknownEnumValueException;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum CellStatus {
    UNCOVERED("uncovered"),
    COVERED("covered"),
    FLAGGED("flagged");

    private String value;

    @JsonValue
    public String getValue() {
        return value;
    }

    @JsonCreator
    public static CellStatus of(String value) {
        if (null == value || value.isEmpty()) {
            return null;
        }

        for (CellStatus item : CellStatus.values()) {
            if (value.equals(item.getValue())) {
                return item;
            }
        }

        throw new UnknownEnumValueException("CellStatus: unknown value: " + value);
    }

    CellStatus(String value) {
        this.value = value;
    }
}
