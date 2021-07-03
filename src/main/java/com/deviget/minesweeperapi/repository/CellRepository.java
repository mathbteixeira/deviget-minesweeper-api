package com.deviget.minesweeperapi.repository;

import com.deviget.minesweeperapi.model.Cell;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface CellRepository extends JpaRepository<Cell, Long> {

    @Query("SELECT c FROM Cell c WHERE c.game.id = :gameId AND c.rowNum = :row AND c.columnNum = :column")
    Cell findByGameIdAndRowAndColumn(
            @Param("gameId") Long gameId, @Param("row") Integer row, @Param("column") Integer column);
}
