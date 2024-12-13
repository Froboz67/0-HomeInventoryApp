package com.techelevator.dao;

import com.techelevator.model.Room;

import java.util.List;

public interface RoomDao {
    List<Room> getRooms(int userId);

    Room getRoomName(int itemId);
}
