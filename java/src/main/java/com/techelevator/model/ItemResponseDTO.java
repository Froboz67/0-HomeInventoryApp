package com.techelevator.model;

import java.util.List;

public class ItemResponseDTO {

    private List<Item> itemList;

    public ItemResponseDTO(List<Item> itemList) {
        this.itemList = itemList;
    }

    public List<Item> getItemList() {
        return itemList;
    }

}
