package com.deviget.minesweeperapi.controller;

import com.deviget.minesweeperapi.model.Cell;
import com.deviget.minesweeperapi.service.MinesweeperConfigService;
import com.deviget.minesweeperapi.vo.CellVO;
import com.deviget.minesweeperapi.vo.GameVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/minesweeper/")
public class MinesweeperApiController {

    @Autowired
    private MinesweeperConfigService service;

    @PostMapping(value = "/createGame/", consumes = "application/json")
    public ResponseEntity<?> createConfig(@RequestBody GameVO game) {
        String response = service.configureGame(game);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping(value = "/revealCell/{id}")
    public ResponseEntity<?> revealCell(@PathVariable("id") Long id) {
        String response = service.revealCell(id);
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(response);
    }

    @GetMapping(value = "/flagCell/{id}")
    public ResponseEntity<?> flagCell(@PathVariable("id") Long id) {
        String response = service.flagCell(id);
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(response);
    }

    @GetMapping(value = "/getCells/{gameId}")
    public List<CellVO> getCells(@PathVariable("gameId") Long gameId) {
        return service.getCells(gameId);
    }

    @GetMapping(value = "/getGame/{gameId}")
    public GameVO getGame(@PathVariable("gameId") Long gameId) {
        return service.getGame(gameId);
    }

}