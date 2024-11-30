package com.techelevator.model;

import java.time.LocalDateTime;

public class Photo {

    private int photoId;
    private int itemId;
    private String name;
    private String photoUrl;
    private LocalDateTime uploadedAt;


    public Photo() {

    }

    public Photo(int photoId, int itemId, String name, String photoUrl, LocalDateTime uploadedAt) {
        this.photoId = photoId;
        this.itemId = itemId;
        this.name = name;
        this.photoUrl = photoUrl;
        this.uploadedAt = uploadedAt;
    }

    public int getPhotoId() {
        return photoId;
    }

    public void setPhotoId(int photoId) {
        this.photoId = photoId;
    }

    public int getItemId() {
        return itemId;
    }

    public void setItemId(int itemId) {
        this.itemId = itemId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhotoUrl() {
        return photoUrl;
    }

    public void setPhotoUrl(String photoUrl) {
        this.photoUrl = photoUrl;
    }

    public LocalDateTime getUploadedAt() {
        return uploadedAt;
    }

    public void setUploadedAt(LocalDateTime uploadedAt) {
        this.uploadedAt = uploadedAt;
    }

    @Override
    public String toString() {
        return "Photo{" +
                "photoId=" + photoId +
                ", itemId=" + itemId +
                ", name='" + name + '\'' +
                ", photoUrl='" + photoUrl + '\'' +
                ", uploadedAt=" + uploadedAt +
                '}';
    }
}
