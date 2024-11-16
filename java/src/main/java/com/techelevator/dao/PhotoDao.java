package com.techelevator.dao;

import com.techelevator.model.Photo;
import org.springframework.stereotype.Component;

@Component
public interface PhotoDao {
    Photo savePhoto(Photo photo, int userId);

    Photo updatePhoto(Photo photo, int itemId);

    Photo getPhoto(int itemId);

    void deletePhoto(Photo photo, int itemId);
}
