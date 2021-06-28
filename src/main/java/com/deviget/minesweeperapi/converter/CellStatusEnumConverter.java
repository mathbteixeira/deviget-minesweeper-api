package com.deviget.minesweeperapi.converter;

import com.deviget.minesweeperapi.model.CellStatus;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class CellStatusEnumConverter implements Converter<String, CellStatus> {
    @Override
    public CellStatus convert(String value) {
        return CellStatus.valueOf(value);
    }
}
