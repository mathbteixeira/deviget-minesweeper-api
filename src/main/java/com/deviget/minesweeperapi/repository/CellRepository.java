package com.deviget.minesweeperapi.repository;

import com.deviget.minesweeperapi.model.Cell;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CellRepository extends JpaRepository<Cell, Long> {

    Cell findByRowNumAndColumnNumAndGameId(Integer rowNum, Integer columnNum, Long gameId);

    long countByStatusAndGameId(String status, Long gameId);
}
