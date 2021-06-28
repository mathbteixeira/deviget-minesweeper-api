package com.deviget.minesweeperapi.controller;

import com.deviget.minesweeperapi.model.MinesweeperConfig;
import com.deviget.minesweeperapi.service.MinesweeperConfigService;
import com.deviget.minesweeperapi.vo.CellVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/minesweeper/")
public class MinesweeperApiController {

    @Autowired
    private MinesweeperConfigService service;

    @GetMapping(value = "/createGame/{rows}/{columns}/{mines}", produces = "application/json")
    public void createConfig(@PathVariable("rows") Integer rows,
                             @PathVariable("columns") Integer columns,
                             @PathVariable("mines") Integer mines) {
        service.configureGame(rows, columns, mines);
    }

    @PostMapping(consumes = { "application/json" })
    public void updateCellStatus(@RequestBody CellVO cell) {
        service.updateCellStatus(cell);
    }
}