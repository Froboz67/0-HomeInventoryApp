package com.techelevator.dao;

import com.techelevator.model.Item;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface ItemDao {

    void saveItem(Item item, int userId);

    List<Item> getAllItems(int userId);

    Item updateItem(Item item, int userId);

    Item getItem(int itemId, int userId);
}
