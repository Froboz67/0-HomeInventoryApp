package com.techelevator.model;

public class Room {

    private int roomId;
    private int userId;
    private String roomName;

    public Room(int roomId, int userId, String roomName) {
        this.roomId = roomId;
        this.userId = userId;
        this.roomName = roomName;
    }

    public int getRoomId() {
        return roomId;
    }

    public void setRoomId(int roomId) {
        this.roomId = roomId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getRoomName() {
        return roomName;
    }

    public void setRoomName(String roomName) {
        this.roomName = roomName;
    }

    @Override
    public String toString() {
        return "Room{" +
                "roomId=" + roomId +
                ", userId=" + userId +
                ", roomName='" + roomName + '\'' +
                '}';
    }
}
