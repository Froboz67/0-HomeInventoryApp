package com.techelevator.model;

import java.util.List;

public class StatesResponseDto {

    private List<State> data;

    public StatesResponseDto(List<State> data) {
        this.data = data;
    }

    public List<State> getData() {
        return data;
    }
}
