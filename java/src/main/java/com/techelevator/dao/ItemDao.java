package com.techelevator.dao;

import com.techelevator.model.Item;
import org.springframework.stereotype.Component;

@Component
public interface ItemDao {

    Item saveItem(Item item, int userId);
}
