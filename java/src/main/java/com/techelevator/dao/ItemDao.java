package com.techelevator.dao;

import com.techelevator.model.Item;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface ItemDao {

    Item saveItem(Item item, int userId);

    List<Item> getAllItems(int userId);
}
