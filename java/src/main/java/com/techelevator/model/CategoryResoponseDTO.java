package com.techelevator.model;

import java.util.List;

public class CategoryResoponseDTO {

    private final List<Category> categories;

    public  CategoryResoponseDTO(List<Category> categories) {
        this.categories = categories;
    }

    public List<Category> getCategories() {
        return categories;
    }
}
