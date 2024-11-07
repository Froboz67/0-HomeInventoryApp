package com.techelevator.dao;

import com.techelevator.model.ItemPhoto;
import org.springframework.stereotype.Component;

@Component
public interface ItemPhotoDao {
    void savePhoto(ItemPhoto itemPhoto, int userId);
}
