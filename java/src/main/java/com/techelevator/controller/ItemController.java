package com.techelevator.controller;

import com.techelevator.dao.ItemDao;
import com.techelevator.dao.PhotoDao;
import com.techelevator.dao.UserDao;
import com.techelevator.model.Item;
import com.techelevator.model.ItemResponseDTO;
import com.techelevator.model.Photo;
import com.techelevator.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.Principal;
import java.util.List;

@RestController
// desktop app
//@CrossOrigin (origins = "http://127.0.0.1:5173")
// laptop app
@CrossOrigin

@PreAuthorize("isAuthenticated()")
public class ItemController {

    @Autowired
    private ItemDao itemDao;
    @Autowired
    private UserDao userDao;
    @Autowired
    private PhotoDao photoDao;

    @PostMapping("/item")
    public ResponseEntity<Item> saveItem(@RequestBody Item item, Principal principal) {

//        int userId = item.getUserId();
//        System.out.println("userId = " + userId);
//        System.out.println("userDao userId = " + userDao.getUserById(item.getUserId()));
//        String username = principal.getName();
//        User user = userDao.getUserByUsername(username);
//        if (userId != user.getId()) {
//            throw new ResponseStatusException(HttpStatus.FORBIDDEN);
//        }

        User user = userDao.getUserByUsername(principal.getName());
        int id = user.getId();
        System.out.println("the user id is + " + id);
        itemDao.saveItem(item, user.getId());
        return ResponseEntity.status(HttpStatus.CREATED).body(item);
    }
    @PostMapping("/update/{itemId}")
    public ResponseEntity<Item> updateItem(@PathVariable int itemId, @RequestBody Item item, Principal principal) {
        User user = userDao.getUserByUsername(principal.getName());
        itemDao.updateItem(item, user.getId());
        System.out.println(user.getId());
        return ResponseEntity.status(HttpStatus.OK).body(item);
    }
    @DeleteMapping("/delete/{itemId}")
    public ResponseEntity<Item> deleteItem(@PathVariable int itemId, Item item, Principal principal) {


        // Logger supplied by spring boot SLF4j...
        Logger logger = LoggerFactory.getLogger(this.getClass());

        User user = userDao.getUserByUsername(principal.getName());
        Photo photo = photoDao.getPhoto(itemId);
        System.out.println("this is the pre-deleted photo " + photo);
        if (photo != null) {
            String photoPath = photo.getPhotoUrl() + photo.getName();

            try {
                Path path = Paths.get(photoPath);
                boolean deleted = Files.deleteIfExists(path);
                if (deleted) {
                    logger.info("Successfully deleted file at path: {}", photoPath);
                } else {
                    logger.warn("File not found at path: {}", photoPath);
                }
            } catch (IOException e) {
                logger.error("Error occurred while deleting file: {}", photoPath, e);
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
            }
        }
        itemDao.deleteItem(item, user.getId());
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
    }

    @GetMapping("/list-items")
    public ItemResponseDTO getItemsList(Principal principal) {

        String currentUser = principal.getName();
        User user = userDao.getUserByUsername(currentUser);

        final List<Item> itemList = this.itemDao.getAllItems(user.getId());
        return new ItemResponseDTO(itemList);
    }
    @GetMapping("/itemdetails/{itemId}")
    public Item getItem(@PathVariable int itemId, Principal principal) {


        String currentUser = principal.getName();
        System.out.println("inside getItem:  " + currentUser);
        User user = userDao.getUserByUsername(currentUser);
        int userId = user.getId();
        /*
        I had to create an overloaded method in order to get the item with only the
        itemId. Then I get the item Id from the current this.item instance and
        compare the userId of the current instance to be certain the authenticated
        user is authorized to view the current item
         */
        Item item = itemDao.getItem(itemId);

        if (item.getUserId() != userId) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN);
        }
        return itemDao.getItem(itemId, user.getId());
    }
}
