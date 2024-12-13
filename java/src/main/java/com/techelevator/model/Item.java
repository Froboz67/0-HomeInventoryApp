package com.techelevator.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;


public class Item {

    private int itemId;
    private int userId;
    private String name;
    private String category;
    private LocalDate purchaseDate;
    private BigDecimal purchasePrice;
    private BigDecimal value;
    @JsonProperty("isValuable")
    private Boolean isValuable;
    private String notes;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    @JsonProperty("categoryId")
    private int category_id;


    public Item() {

    }

    public Item(int itemId, int userId, String name, String category, LocalDate purchaseDate, BigDecimal purchasePrice,
                BigDecimal value, Boolean isValuable, String notes, LocalDateTime createdAt, LocalDateTime updatedAt, int category_id) {
        this.itemId = itemId;
        this.userId = userId;
        this.name = name;
        this.category = category;
        this.purchaseDate = purchaseDate;
        this.purchasePrice = purchasePrice;
        this.value = value;
        this.isValuable = isValuable;
        this.notes = notes;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.category_id = category_id;
    }

    public int getItemId() {
        return itemId;
    }

    public void setItemId(int itemId) {
        this.itemId = itemId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public LocalDate getPurchaseDate() {
        return purchaseDate;
    }

    public void setPurchaseDate(LocalDate purchaseDate) {
        this.purchaseDate = purchaseDate;
    }

    public BigDecimal getPurchasePrice() {
        return purchasePrice;
    }

    public void setPurchasePrice(BigDecimal purchasePrice) {
        this.purchasePrice = purchasePrice;
    }

    public BigDecimal getValue() {
        return value;
    }

    public void setValue(BigDecimal value) {
        this.value = value;
    }

    public Boolean getValuable() {
        return isValuable;
    }

    public void setValuable(Boolean valuable) {
        isValuable = valuable;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public int getCategory_id() {
        return category_id;
    }

    public void setCategory_id(int category_id) {
        this.category_id = category_id;
    }

    @Override
    public String toString() {
        return "Item{" +
                "itemId=" + itemId +
                ", userId=" + userId +
                ", name='" + name + '\'' +
                ", category='" + category + '\'' +
                ", purchaseDate=" + purchaseDate +
                ", purchasePrice=" + purchasePrice +
                ", value=" + value +
                ", isValuable=" + isValuable +
                ", notes='" + notes + '\'' +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                ", category_id=" + category_id +
                '}';
    }
}
