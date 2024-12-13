package com.techelevator.model;

import java.util.List;

public class RoomResponseDTO {

    private final List<Room> rooms;

    public RoomResponseDTO(List<Room> rooms) {
        this.rooms = rooms;
    }

    public List<Room> getRooms() {
        return rooms;
    }
}
