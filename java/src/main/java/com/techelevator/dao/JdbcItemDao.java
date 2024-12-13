package com.techelevator.dao;

import com.techelevator.exception.DaoException;
import com.techelevator.model.Item;
import org.springframework.jdbc.CannotGetJdbcConnectionException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Component
public class JdbcItemDao implements ItemDao{

    JdbcTemplate jdbcTemplate;

    UserDao userDao;

    public JdbcItemDao(DataSource dataSource){
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public Item saveItem(Item item, int userId) {

        System.out.println("Item object: " + item);

        final String sql ="INSERT INTO public.items(\n" +
                "\tuser_id, i_name, category, purchase_date, purchase_price, i_value, is_valuable, notes, category_id)\n" +
                "\tVALUES (?, ?, ?, ?, ?, ?, ?, ?, ?) RETURNING item_id";

        try {
            Integer itemId = jdbcTemplate.queryForObject(sql, int.class, userId, item.getName(), item.getCategory(), item.getPurchaseDate(),
                    item.getPurchasePrice(), item.getValue(), item.getValuable(), item.getNotes(), item.getCategory_id());
            if (itemId != null) {
                item.setItemId(itemId);
            } else {
                System.err.println("Item id is returned as null");
            }
        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Unable to connect to server or database", e);
        }
        System.out.println(item);
        return item;
    }
    @Override
    public Item getItem(int itemId) {
        Item item = null;
        final String sql = "SELECT i.item_id, i.user_id, i.i_name, c.category_name AS category, i.purchase_date, i.purchase_price, i.i_value, i.is_valuable, i.notes, i.created_at, i.updated_at, i.category_id\n" +
                "\tFROM items AS i\n" +
                "\tJOIN item_category AS c ON i.category_id = c.category_id\n" +
                "\tWHERE item_id = ?";
        try {
            final SqlRowSet results = jdbcTemplate.queryForRowSet(sql, itemId);
            if (results.next()) {
                item = mapRowToItem(results);
            }
        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Unable to connect to server or database", e);
        }
        return item;
    }
    @Override
    public Item getItem(int itemId, int userId) {
        Item item = null;
        final String sql = "SELECT i.item_id, i.user_id, i.i_name, c.category_name AS category, i.purchase_date, i.purchase_price, i.i_value, i.is_valuable, i.notes, i.created_at, i.updated_at, i.category_id\n" +
                "\tFROM items AS i\n" +
                "\tJOIN item_category AS c ON i.category_id = c.category_id\n" +
                "\tWHERE item_id = ?";
        try {
            final SqlRowSet results = jdbcTemplate.queryForRowSet(sql, itemId);
            if (results.next()) {
                item = mapRowToItem(results);
            }
        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Unable to connect to server or database", e);
        }
        System.out.println("item from get method: " + item);
        return item;
    }

    @Override
    public List<Item> getAllItems(int userId) {
        final List<Item> itemsList = new ArrayList<>();

        final String sql = "SELECT i.item_id, i.user_id, i.i_name, c.category_name AS category, i.purchase_date, i.purchase_price, i.i_value, i.is_valuable, i.notes, i.created_at, i.updated_at, i.category_id\n" +
                "\tFROM items AS i\n" +
                "\tJOIN item_category AS c ON i.category_id = c.category_id\n" +
                "\tWHERE user_id = ? ORDER by i_name ASC;";
        try {

            final SqlRowSet results = jdbcTemplate.queryForRowSet(sql, userId);
            while (results.next()) {
                itemsList.add(mapRowToItem(results));
            }
        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("unable to connect to server or database", e);
        }
        return itemsList;
    }

    @Override
    public Item updateItem(Item item, int userId) {
        Item updatedItem = null;
        item.setUpdatedAt(LocalDateTime.now());
        final String sql = "UPDATE public.items\n" +
                "\tSET i_name=?, category=?, purchase_date=?, purchase_price=?, i_value=?, is_valuable=?, notes=?, created_at=?, updated_at=?, category_id=?\n" +
                "\tWHERE item_id=?";
        try {
            int numberOfRowsAffected = jdbcTemplate.update(sql, item.getName(), item.getCategory(), item.getPurchaseDate(),
                    item.getPurchasePrice(), item.getValue(), item.getValuable(), item.getNotes(), item.getCreatedAt(), item.getUpdatedAt(), item.getCategory_id(), item.getItemId());
            updatedItem = getItem(item.getItemId(), item.getUserId());
            if (numberOfRowsAffected == 0) {
                throw new DaoException("zero rows affected");
            }
        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("unable to connect to database", e);
        }
        System.out.println(updatedItem);
        return updatedItem;
    }

    @Override
    public Item deleteItem(Item item, int userId) {
        final String aSql = "DELETE FROM public.item_photos\n" +
                "\tWHERE item_id = ?";
        try {
            jdbcTemplate.update(aSql, item.getItemId());
        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("unable to connect to database", e);

        }
        final String sql = "DELETE FROM public.items\n" +
                "\tWHERE item_id = ?";
        try {
            jdbcTemplate.update(sql, item.getItemId());
        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("unable to connect to database", e);
        }
        System.out.println("the item was deleted");
        return null;
    }

    Item mapRowToItem(SqlRowSet rowSet) {
        Item item = new Item();
        item.setItemId(rowSet.getInt("item_id"));
        item.setUserId(rowSet.getInt("user_id"));
        item.setName(rowSet.getString("i_name"));
        item.setCategory(rowSet.getString("category"));

        java.sql.Date purchaseDate = rowSet.getDate("purchase_date");
        if (purchaseDate != null) {
            item.setPurchaseDate(purchaseDate.toLocalDate());
        } else {
            item.setPurchaseDate(null);
        }

        item.setPurchasePrice(rowSet.getBigDecimal("purchase_price"));
        item.setValue(rowSet.getBigDecimal("i_value"));
        item.setValuable(rowSet.getBoolean("is_valuable"));
        item.setNotes(rowSet.getString("notes"));
        java.sql.Date createdAt = rowSet.getDate("created_at");
        if (createdAt != null) {
            item.setCreatedAt(createdAt.toLocalDate().atTime(LocalTime.now()));
        } else {
            item.setCreatedAt(null);
        }
        java.sql.Date updatedAt = rowSet.getDate("updated_at");
        if (updatedAt != null) {
            item.setUpdatedAt(updatedAt.toLocalDate().atTime(LocalTime.now()));
        } else {
            item.setUpdatedAt(null);
        }
        item.setCategory_id(rowSet.getInt("category_id"));
        return item;
    }

}
