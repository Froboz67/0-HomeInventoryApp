package com.techelevator.model;

public class Room {

    private int roomId;
    private String roomName;
    private boolean isDefault;
    private int createdByUserId;

    public Room(int roomId, String roomName, boolean isDefault, int createdByUserId) {
        this.roomId = roomId;
        this.roomName = roomName;
        this.isDefault = isDefault;
        this.createdByUserId = createdByUserId;
    }

    public Room() {

    }

    public int getRoomId() {
        return roomId;
    }

    public void setRoomId(int roomId) {
        this.roomId = roomId;
    }

    public String getRoomName() {
        return roomName;
    }

    public void setRoomName(String roomName) {
        this.roomName = roomName;
    }

    public boolean isDefault() {
        return isDefault;
    }

    public void setDefault(boolean aDefault) {
        isDefault = aDefault;
    }

    public int getCreatedByUserId() {
        return createdByUserId;
    }

    public void setCreatedByUserId(int createdByUserId) {
        this.createdByUserId = createdByUserId;
    }

    @Override
    public String toString() {
        return "Room{" +
                "roomId=" + roomId +
                ", roomName='" + roomName + '\'' +
                ", isDefault=" + isDefault +
                ", createdByUserId=" + createdByUserId +
                '}';
    }
}
