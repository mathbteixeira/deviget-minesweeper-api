package com.deviget.minesweeperapi.controller;

import com.deviget.minesweeperapi.service.CellService;
import com.deviget.minesweeperapi.service.GameService;
import com.deviget.minesweeperapi.vo.CellVO;
import com.deviget.minesweeperapi.vo.GameVO;
import com.sun.istack.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/minesweeper/")
public class MinesweeperApiController {

    @Autowired
    private GameService gameService;

    @Autowired
    private CellService cellService;

    @PostMapping(value = "/game/", consumes = "application/json")
    public ResponseEntity<GameVO> createGame(@RequestBody @NotNull GameVO game) {
        try {
            GameVO response = gameService.createGame(game);
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        }
        catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }

    @GetMapping(value = "/game/{gameId}")
    public ResponseEntity<GameVO> getGame(@PathVariable("gameId") Long gameId) {
        GameVO response = gameService.getGame(gameId);
        if (response == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
        else {
            return ResponseEntity.status(HttpStatus.OK).body(response);
        }
    }

    @GetMapping(value = "/game/pause/{gameId}")
    public ResponseEntity<GameVO> pauseGame(@PathVariable("gameId") Long gameId) {
        GameVO response = gameService.pauseGame(gameId);
        if (response == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
        else {
            return ResponseEntity.status(HttpStatus.OK).body(response);
        }
    }

    @GetMapping(value = "/game/resume/{gameId}")
    public ResponseEntity<GameVO> resumeGame(@PathVariable("gameId") Long gameId) {
        GameVO response = gameService.resumeGame(gameId);
        if (response == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
        else {
            return ResponseEntity.status(HttpStatus.OK).body(response);
        }
    }

    @GetMapping(value = "/game/cells/{gameId}")
    public ResponseEntity<List<CellVO>> getCells(@PathVariable("gameId") Long gameId) {
        List<CellVO> response = cellService.getCells(gameId);
        if (response == null || response.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
        else {
            return ResponseEntity.status(HttpStatus.OK).body(response);
        }
    }

    @GetMapping(value = "/cell/reveal/{cellId}")
    public ResponseEntity<GameVO> revealCell(@PathVariable("cellId") Long cellId) {
        GameVO response = cellService.revealCell(cellId);
        if (response == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
        else {
            return ResponseEntity.status(HttpStatus.OK).body(response);
        }
    }

    @GetMapping(value = "/cell/flag/{cellId}")
    public ResponseEntity<CellVO> flagCell(@PathVariable("cellId") Long cellId) {
        CellVO response = cellService.flagCell(cellId);
        if (response == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
        else {
            return ResponseEntity.status(HttpStatus.OK).body(response);
        }
    }

}