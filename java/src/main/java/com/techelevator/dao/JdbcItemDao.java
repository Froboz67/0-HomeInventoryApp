package com.techelevator.dao;

import com.techelevator.exception.DaoException;
import com.techelevator.model.Item;
import org.springframework.jdbc.CannotGetJdbcConnectionException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;

@Component
public class JdbcItemDao implements ItemDao{

    JdbcTemplate jdbcTemplate;

    public JdbcItemDao(DataSource dataSource){
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public Item saveItem(Item item, int userId) {

        String sql ="INSERT INTO public.items(\n" +
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
}
