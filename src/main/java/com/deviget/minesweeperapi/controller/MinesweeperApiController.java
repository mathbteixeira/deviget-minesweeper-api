package com.deviget.minesweeperapi.controller;

import com.deviget.minesweeperapi.model.Cell;
import com.deviget.minesweeperapi.service.MinesweeperConfigService;
import com.deviget.minesweeperapi.vo.CellVO;
import com.deviget.minesweeperapi.vo.GameVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/minesweeper/")
public class MinesweeperApiController {

    @Autowired
    private MinesweeperConfigService service;

    @PostMapping(value = "/createGame/", consumes = "application/json")
    public void createConfig(@RequestBody GameVO game) {
        service.configureGame(game);
    }

    @GetMapping(value = "/revealCell/{id}")
    public ResponseEntity<?> revealCell(@PathVariable("id") Long id) {
        service.revealCell(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping(value = "/flagCell/{id}")
    public ResponseEntity<?> flagCell(@PathVariable("id") Long id) {
        service.flagCell(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping(value = "/getCells/{gameId}")
    public List<CellVO> getCells(@PathVariable("gameId") Long gameId) {
        return service.getCells(gameId);
    }

}