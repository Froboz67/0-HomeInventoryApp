package com.techelevator.dao;

import com.techelevator.model.Category;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface CategoryDao {
    List<Category> getCategories(int userId);
}
