package com.techelevator.dao;

import com.techelevator.exception.DaoException;
import com.techelevator.model.ItemCategory;
import org.springframework.jdbc.CannotGetJdbcConnectionException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;

import java.util.ArrayList;
import java.util.List;

public class JdbcItemCategoryDao implements ItemCategoryDao{

    JdbcTemplate jdbcTemplate;

    @Override
    public List<ItemCategory> getCategories(int userId) {
        final List<ItemCategory> categories = new ArrayList<>();

        final String sql = "SELECT category_id, category_name, is_default, created_by_user_id\n" +
                "\tFROM public.item_category\n" +
                "\tWHERE created_by_user_id = ?";

        try {

            final SqlRowSet results = jdbcTemplate.queryForRowSet(sql, userId);
            while (results.next()) {
                categories.add(mapRowToItemCategory(results));
            }
        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("unable to connect to server or database", e);
        }
        return categories;
    }

    ItemCategory mapRowToItemCategory(SqlRowSet rowSet) {
        ItemCategory category = new ItemCategory();
        category.setCategoryId(rowSet.getInt("category_id"));
        category.setCategoryName(rowSet.getString("category_name"));
        category.setDefault(rowSet.getBoolean("is_default"));
        category.setCreatedByUserId(rowSet.getInt("created_by_user_id"));
        return category;
    }

}
