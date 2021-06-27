package com.deviget.minesweeperapi.controller;

import com.deviget.minesweeperapi.model.MinesweeperConfig;
import com.deviget.minesweeperapi.service.MinesweeperConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/minesweeper/")
public class MinesweeperApiController {

    @Autowired
    private MinesweeperConfigService service;

    @GetMapping(value = "createGame/{rows}/{columns}/{mines}", produces = "aplication/json")
    public void createConfig(@PathVariable("rows") Integer rows,
                             @PathVariable("columns") Integer columns,
                             @PathVariable("mines") Integer mines) {
        service.configureGame(rows, columns, mines);
    }
}