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
    public void saveItem(Item item, int userId) {

        final String sql ="INSERT INTO public.items(\n" +
                "\tuser_id, i_name, category, purchase_date, purchase_price, i_value, is_valuable, notes)\n" +
                "\tVALUES (?, ?, ?, ?, ?, ?, ?, ?) RETURNING item_id";

        try {
            Integer itemId = jdbcTemplate.queryForObject(sql, int.class, userId, item.getName(), item.getCategory(), item.getPurchaseDate(),
                    item.getPurchasePrice(), item.getValue(), item.getValuable(), item.getNotes());
            if (itemId != null) {
                item.setItemId(itemId);
            } else {
                System.err.println("Item id is returned as null");
            }
        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Unable to connect to server or database", e);
        }
        System.out.println(item);
    }
    @Override
    public Item getItem(int itemId, int userId) {
        Item item = null;
        final String sql = "SELECT item_id, user_id, i_name, category, purchase_date, purchase_price, i_value, is_valuable, notes, created_at, updated_at\n" +
                "\tFROM public.items\n" +
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
    public List<Item> getAllItems(int userId) {
        final List<Item> itemsList = new ArrayList<Item>();
        final String sql = "SELECT item_id, user_id, i_name, category, purchase_date, purchase_price, i_value, is_valuable, notes, created_at, updated_at\n" +
                "\tFROM public.items\n" +
                "\tWHERE user_id = ?;";
        try {
            // do something here
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
                "\tSET user_id=?, i_name=?, category=?, purchase_date=?, purchase_price=?, i_value=?, is_valuable=?, notes=?, created_at=?, updated_at=?\n" +
                "\tWHERE item_id=?";
        try {
            int numberOfRowsAffected = jdbcTemplate.update(sql, item.getUserId(), item.getName(), item.getCategory(), item.getPurchaseDate(),
                    item.getPurchasePrice(), item.getValue(), item.getValuable(), item.getNotes(), item.getCreatedAt(), item.getUpdatedAt(), item.getItemId());
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
        return item;
    }

}
