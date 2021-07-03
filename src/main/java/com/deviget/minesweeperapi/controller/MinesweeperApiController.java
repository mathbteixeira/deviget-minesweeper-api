package com.deviget.minesweeperapi.controller;

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
    public GameVO createConfig(@RequestBody GameVO game) {
        return service.configureGame(game);
    }

    @GetMapping(value = "/getGame/{gameId}")
    public GameVO getGame(@PathVariable("gameId") Long gameId) {
        return service.getGame(gameId);
    }

    @GetMapping(value = "/revealCell/{id}")
    public ResponseEntity<CellVO> revealCell(@PathVariable("id") Long id) {
        CellVO response = service.revealCell(id);
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

}