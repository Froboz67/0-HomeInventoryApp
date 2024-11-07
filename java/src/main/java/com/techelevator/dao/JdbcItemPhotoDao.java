package com.techelevator.dao;


import com.techelevator.exception.DaoException;
import com.techelevator.model.ItemPhoto;
import org.springframework.jdbc.CannotGetJdbcConnectionException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.time.LocalTime;

@Component
public class JdbcItemPhotoDao implements ItemPhotoDao{

    JdbcTemplate jdbcTemplate;

    UserDao userDao;

    ItemDao itemDao;

    public JdbcItemPhotoDao(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public void savePhoto(ItemPhoto itemPhoto, int itemId) {

        final String sql = "INSERT INTO public.item_photos(\n" +
                "\titem_id, photo_url, uploaded_at)\n" +
                "\tVALUES (?, ?, ?)\n" +
                "\tRETURNING photo_id";

        try {
            Integer photoId = jdbcTemplate.queryForObject(sql, int.class, itemId, itemPhoto.getPhotoUrl(), itemPhoto.getUploadedAt());
            if (photoId != null) {
                itemPhoto.setPhotoId(photoId);
            } else {
                System.err.println("Photo id was returned as null");
            }
        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Unable to connect to server or database", e);
        }
        System.out.println(itemPhoto);
    }

    ItemPhoto mapRowtoItemPhoto(SqlRowSet rowSet) {
        ItemPhoto itemPhoto = new ItemPhoto();
        itemPhoto.setPhotoId(rowSet.getInt("photo_id"));
        itemPhoto.setItemId(rowSet.getInt("item_id"));
        itemPhoto.setPhotoUrl(rowSet.getString("photo_url"));
        java.sql.Date uploadedAt = rowSet.getDate("uploaded_at");
        if (uploadedAt != null) {
            itemPhoto.setUploadedAt(uploadedAt.toLocalDate().atTime(LocalTime.now()));
        } else {
            itemPhoto.setUploadedAt(null);
        }
        return itemPhoto;
    }

}
