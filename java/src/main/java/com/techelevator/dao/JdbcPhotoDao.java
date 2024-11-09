package com.techelevator.dao;


import com.techelevator.exception.DaoException;
import com.techelevator.model.Photo;
import org.springframework.jdbc.CannotGetJdbcConnectionException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.time.LocalTime;

@Component
public class JdbcPhotoDao implements PhotoDao {

    JdbcTemplate jdbcTemplate;

    UserDao userDao;

    ItemDao itemDao;

    public JdbcPhotoDao(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public Photo savePhoto(Photo photo, int itemId) {

        final String sql = "INSERT INTO public.item_photos(\n" +
                "\titem_id, photo_name, photo_url)\n" +
                "\tVALUES (?, ?, ?)\n" +
                "\tRETURNING photo_id";

        try {
            Integer photoId = jdbcTemplate.queryForObject(sql, int.class, photo.getItemId(), photo.getName(), photo.getPhotoUrl());
            if (photoId != null) {
                photo.setPhotoId(photoId);
            } else {
                System.err.println("Photo id was returned as null");
            }
        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Unable to connect to server or database", e);
        }
        System.out.println(photo);
        return photo;
    }
    @Override
    public Photo getPhoto(int itemId) {
        Photo photo = null;
        final String sql = "SELECT photo_id, item_id, photo_name, photo_url, uploaded_at\n" +
                "\tFROM public.item_photos\n" +
                "\tWHERE item_id = ?";
        try {
            final SqlRowSet results = jdbcTemplate.queryForRowSet(sql, itemId);
            if (results.next()) {
                photo = mapRowToPhoto(results);
            }
        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Unable to connect to server or database", e);
        }
        System.out.println(photo);
        return photo;
    }
    @Override
    public void deletePhoto(Photo photo, int itemId) {
        final String sql = "DELETE FROM public.item_photos\n" +
                "\tWHERE item_id = ?";
        try {
            jdbcTemplate.update(sql, photo.getItemId());
        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("unable to connect to database", e);
        }
        System.out.println("the photo was deleted");
    }

    Photo mapRowToPhoto(SqlRowSet rowSet) {
        Photo photo = new Photo();
        photo.setPhotoId(rowSet.getInt("photo_id"));
        photo.setItemId(rowSet.getInt("item_id"));
        photo.setName(rowSet.getString("photo_name"));
        photo.setPhotoUrl(rowSet.getString("photo_url"));
        java.sql.Date uploadedAt = rowSet.getDate("uploaded_at");
        if (uploadedAt != null) {
            photo.setUploadedAt(uploadedAt.toLocalDate().atTime(LocalTime.now()));
        } else {
            photo.setUploadedAt(null);
        }
        return photo;
    }

}
