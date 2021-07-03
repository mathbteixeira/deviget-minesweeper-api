package com.deviget.minesweeperapi.converter;

import com.deviget.minesweeperapi.model.Game;
import com.deviget.minesweeperapi.vo.CellVO;
import com.deviget.minesweeperapi.vo.GameVO;

import java.util.ArrayList;
import java.util.List;

public class GameConverter {

    public static GameVO convertToGameVO(Game game) {
        GameVO gameVo = new GameVO();
        gameVo.setId(game.getId());
        gameVo.setNumOfRows(game.getNumOfRows());
        gameVo.setNumOfColumns(game.getNumOfColumns());
        gameVo.setNumOfMines(game.getNumOfMines());
        gameVo.setStatus(game.getStatus());
        gameVo.setCells(game.getCellVoList());
        return gameVo;
    }
}
