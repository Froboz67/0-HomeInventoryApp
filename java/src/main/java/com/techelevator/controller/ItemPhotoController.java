package com.techelevator.controller;


import com.techelevator.dao.ItemDao;
import com.techelevator.dao.ItemPhotoDao;
import com.techelevator.dao.UserDao;
import com.techelevator.model.ItemPhoto;
import com.techelevator.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.security.Principal;

@RestController
@CrossOrigin (origins = "http://127.0.0.1:5173")
@PreAuthorize("isAuthenticated()")
public class ItemPhotoController {

    @Autowired
    private ItemPhotoDao itemPhotoDao;

    @Autowired
    private UserDao userDao;

    @PostMapping("/photo")
    public ResponseEntity<ItemPhoto> savePhoto(@RequestBody ItemPhoto itemPhoto, Principal principal) {
        User user = userDao.getUserByUsername(principal.getName());
        itemPhotoDao.savePhoto(itemPhoto, user.getId());
        return ResponseEntity.status(HttpStatus.CREATED).body(itemPhoto);
    }
//    @PostMapping("/photo/upload")
//    public ResponseEntity<ItemPhoto> uploadPhoto(@RequestParam("file")MultipartFile file,
//                                                 @RequestParam("itemId") int itemId, Principal principal) {
//        User uesr = userDao.getUserByUsername(principal.getName());
//
//    }

}
