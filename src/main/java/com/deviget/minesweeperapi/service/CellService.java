package com.deviget.minesweeperapi.service;

import com.deviget.minesweeperapi.converter.CellConverter;
import com.deviget.minesweeperapi.converter.GameConverter;
import com.deviget.minesweeperapi.model.Cell;
import com.deviget.minesweeperapi.model.Game;
import com.deviget.minesweeperapi.repository.CellRepository;
import com.deviget.minesweeperapi.vo.CellVO;
import com.deviget.minesweeperapi.vo.GameVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CellService {

    @Autowired
    CellRepository cellRepository;

    @Autowired
    GameService gameService;

    public GameVO revealCell(Long id) {
        Cell cell = cellRepository.getById(id);
        if (!cell.getGame().getStatus().equals(Game.CLOSED)) {
            if (cell.isMine()) {
                Game endedGame = gameService.endGame(cell.getGame());
                return GameConverter.convertToGameVO(endedGame);
            }
            else {
                if (!cell.getStatus().equals(Cell.UNCOVERED)) {
                    Long gameId = cell.getGame().getId();
                    cell = uncoverCell(cell);
                    if (cell != null && cell.getNumOfAdjacentMines() == 0) {
                        revealAdjacentCells(cell);
                    }
                    return gameService.getGame(gameId);
                } else {
                    // cell uncovered
                    return null;
                }
            }
        } else {
            // game closed
            return null;
        }
    }

    private void revealAdjacentCells(Cell cell) {
        List<Cell> cellsWithNoAdjacentMines = new ArrayList<>();
        for (int row = cell.getRowNum() - 1; row <= cell.getRowNum() + 1; row++) {
            for (int col = cell.getColumnNum() - 1; col <= cell.getColumnNum() + 1; col++) {
                if ((col >= 0 && col < cell.getGame().getNumOfColumns())
                        && (row >= 0 && row < cell.getGame().getNumOfRows())
                        && !(row == cell.getRowNum() && col == cell.getColumnNum())) {
                    Cell adjacentCell = cellRepository.findByRowNumAndColumnNumAndGameId(row, col, cell.getGame().getId());
                    if (!adjacentCell.isMine() && !adjacentCell.getStatus().equals(Cell.UNCOVERED)) {
                        adjacentCell = uncoverCell(adjacentCell);
                        // adjacentCell == null -> game win
                        if (adjacentCell != null && adjacentCell.getNumOfAdjacentMines() == 0) {
                            cellsWithNoAdjacentMines.add(adjacentCell);
                        }
                    }
                }
            }
        }
        if (!cellsWithNoAdjacentMines.isEmpty()) {
            for (Cell c : cellsWithNoAdjacentMines) {
                revealAdjacentCells(c);
            }
        }
    }

    private Cell uncoverCell(Cell cell) {
        cell.setStatus(Cell.UNCOVERED);
        Cell savedCell = cellRepository.save(cell);
        long countUncoveredCells = cellRepository.countByStatusAndGameId(Cell.UNCOVERED, cell.getGame().getId());
        if (countUncoveredCells == (((long) cell.getGame().getNumOfRows() * cell.getGame().getNumOfColumns()) - cell.getGame().getNumOfMines())) {
            // game won!
            System.out.println("Game won!");
            return null;
        }
        else {
            return savedCell;
        }
    }

    public CellVO flagCell(Long cellId) {
        Cell cell = cellRepository.getById(cellId);
        if (!cell.getGame().getStatus().equals(Game.CLOSED)) {
            switch (cell.getStatus()) {
                case Cell.COVERED:
                    cell.setStatus(Cell.FLAGGED);
                    cell = cellRepository.save(cell);
                    return CellConverter.convertToCellVO(cell);
                case Cell.FLAGGED:
                    cell.setStatus(Cell.COVERED);
                    cell = cellRepository.save(cell);
                    return CellConverter.convertToCellVO(cell);
                default:
                    //uncovered
                    return null;
            }
        }
        else {
            //game closed
            return null;
        }
    }

    List<Cell> saveCells(Game game, Cell[][] configArray) {
        List<Cell> savedCells = new ArrayList<Cell>();
        for (Cell[] cellArray : configArray) {
            Arrays.stream(cellArray).forEach(c -> {
                c.setGame(game);
                savedCells.add(cellRepository.save(c));
            });
        }
        return savedCells;
    }

    public List<CellVO> getCells(Long gameId) {
        List<Cell> cellList = cellRepository.findAll().stream().filter(cell ->
                cell.getGame().getId() == gameId).collect(Collectors.toList());
        List<CellVO> cellVoList = new ArrayList<>();
        if (!cellList.isEmpty()) {
            cellList.forEach(cell -> {
                CellVO cellVo = CellConverter.convertToCellVO(cell);
                cellVoList.add(cellVo);
            });
        }
        return cellVoList;
    }
}
