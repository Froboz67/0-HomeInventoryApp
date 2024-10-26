package com.techelevator.controller;

import com.techelevator.dao.StateDao;
import com.techelevator.model.State;
import com.techelevator.model.StatesResponseDto;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@CrossOrigin
public class StatesController {

    private StateDao stateDao;

    public StatesController(StateDao stateDao) {
        this.stateDao = stateDao;
    }

    @GetMapping("/states")
    public StatesResponseDto getAll() {
        final List<State> states = this.stateDao.getAll();
        return new StatesResponseDto(states);
    }

}
