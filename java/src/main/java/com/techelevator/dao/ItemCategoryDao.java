package com.techelevator.dao;

import com.techelevator.model.ItemCategory;

import java.util.List;

public interface ItemCategoryDao {
    List<ItemCategory> getCategories(int userId);
}
