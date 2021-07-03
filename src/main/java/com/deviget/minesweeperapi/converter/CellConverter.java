package com.deviget.minesweeperapi.converter;

import com.deviget.minesweeperapi.model.Cell;
import com.deviget.minesweeperapi.vo.CellVO;

public class CellConverter {

    public static CellVO convertToCellVO(Cell cell) {
        CellVO cellVo = new CellVO();
        cellVo.setId(cell.getId());
        cellVo.setRowNum(cell.getRowNum());
        cellVo.setColumnNum(cell.getColumnNum());
        cellVo.setStatus(cell.getStatus());
        cellVo.setMine(cell.isMine() ? "Mine" : "Not a Mine");
        cellVo.setNumOfAdjacentMines(cell.getNumOfAdjacentMines());
        cellVo.setGameId(cell.getGame().getId());
        return cellVo;
    }
}
