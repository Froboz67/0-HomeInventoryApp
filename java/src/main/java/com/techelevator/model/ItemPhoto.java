package com.techelevator.model;

import java.time.LocalDateTime;

public class ItemPhoto {

    private int photoId;
    private int itemId;
    private String photoUrl;
    private LocalDateTime uploadedAt;

    public ItemPhoto() {

    }

    public ItemPhoto(int photoId, int itemId, String photoUrl, LocalDateTime uploadedAt) {
        this.photoId = photoId;
        this.itemId = itemId;
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
        return "ItemPhoto{" +
                "photoId=" + photoId +
                ", itemId=" + itemId +
                ", photoUrl='" + photoUrl + '\'' +
                ", uploadedAt=" + uploadedAt +
                '}';
    }
}
