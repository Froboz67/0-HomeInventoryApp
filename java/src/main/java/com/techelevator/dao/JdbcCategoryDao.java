package com.techelevator.dao;

import com.techelevator.exception.DaoException;
import com.techelevator.model.Category;
import org.springframework.jdbc.CannotGetJdbcConnectionException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;

@Component
public class JdbcCategoryDao implements CategoryDao {

    JdbcTemplate jdbcTemplate;

    public JdbcCategoryDao(DataSource dataSource){
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public List<Category> getCategories(int userId) {
        final List<Category> categories = new ArrayList<>();

        final String sql = "SELECT category_id, category_name, is_default, created_by_user_id\n" +
                "FROM item_category\n" +
                "WHERE is_default = true\n" +
                "   OR created_by_user_id = (\n" +
                "   SELECT user_id FROM users WHERE user_id = ?\n" +
                "   )";

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

    Category mapRowToItemCategory(SqlRowSet rowSet) {
        Category category = new Category();
        category.setCategoryId(rowSet.getInt("category_id"));
        category.setCategoryName(rowSet.getString("category_name"));
        category.setDefault(rowSet.getBoolean("is_default"));
        category.setCreatedByUserId(rowSet.getInt("created_by_user_id"));
        return category;
    }

}
