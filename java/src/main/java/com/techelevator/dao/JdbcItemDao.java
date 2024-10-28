package com.techelevator.dao;

import com.techelevator.exception.DaoException;
import com.techelevator.model.Item;
import org.apache.logging.log4j.message.ExitMessage;
import org.springframework.jdbc.CannotGetJdbcConnectionException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.SqlInOutParameter;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
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

        final String sql ="INSERT INTO public.items(\n" +
                "\tuser_id, i_name, category, purchase_date, purchase_price, i_value, is_valuable, notes)\n" +
                "\tVALUES (?, ?, ?, ?, ?, ?, ?, ?) RETURNING item_id";

        try {
            int itemId = jdbcTemplate.queryForObject(sql, int.class, userId, item.getName(), item.getCategory(), item.getPurchaseDate(),
                    item.getPurchasePrice(), item.getValue(), item.getValuable(), item.getNotes());
        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Unable to connect to server or database", e);
        }
        System.out.println(item);
        return null;
    }
    @Override
    public List<Item> getAllItems(int userId) {
        final List<Item> itemsList = new ArrayList<Item>();
        final String sql = "SELECT item_id, user_id, i_name, category, purchase_date, purchase_price, i_value, is_valuable, notes\n" +
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
        return item;
    }

}
