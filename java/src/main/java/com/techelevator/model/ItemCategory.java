package com.techelevator.model;

public class ItemCategory {

    private int categoryId;
    private String categoryName;
    private Boolean isDefault;
    private int createdByUserId;

    public ItemCategory() {

    }

    public ItemCategory(int categoryId, String categoryName, Boolean isDefault, int createdByUserId) {
        this.categoryId = categoryId;
        this.categoryName = categoryName;
        this.isDefault = isDefault;
        this.createdByUserId = createdByUserId;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public Boolean getDefault() {
        return isDefault;
    }

    public void setDefault(Boolean aDefault) {
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
        return "ItemCategory{" +
                "categoryId=" + categoryId +
                ", categoryName='" + categoryName + '\'' +
                ", isDefault=" + isDefault +
                ", createdByUserId=" + createdByUserId +
                '}';
    }
}
