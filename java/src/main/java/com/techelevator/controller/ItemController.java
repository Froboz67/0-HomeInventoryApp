package com.techelevator.controller;

import com.techelevator.dao.ItemDao;
import com.techelevator.dao.UserDao;
import com.techelevator.model.Item;
import com.techelevator.model.ItemResponseDTO;
import com.techelevator.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.parameters.P;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@CrossOrigin (origins = "http://127.0.0.1:5173")
@PreAuthorize("isAuthenticated()")
public class ItemController {

    @Autowired
    private ItemDao itemDao;
    @Autowired
    private UserDao userDao;

    @PostMapping("/item")
    public ResponseEntity<Item> saveItem(@RequestBody Item item, Principal principal) {
        User user = userDao.getUserByUsername(principal.getName());
        itemDao.saveItem(item, user.getId());
        return ResponseEntity.status(HttpStatus.CREATED).body(item);
    }
    @PostMapping("/update/{itemId}/{userId}")
    public ResponseEntity<Item> updateItem(@PathVariable int itemId, @PathVariable int userId, @RequestBody Item item, Principal principal) {
        User user = userDao.getUserByUsername(principal.getName());
        itemDao.updateItem(item, userId);
        return ResponseEntity.status(HttpStatus.OK).body(item);
    }
    @DeleteMapping("/delete/{userId}/{itemId}")
    public ResponseEntity<Item> deleteItem(@PathVariable int itemId, @PathVariable int userId, Item item, Principal principal) {
        User user = userDao.getUserByUsername(principal.getName());
        itemDao.deleteItem(item, userId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
    }

    @GetMapping("/list-items/{userId}")
    public ItemResponseDTO getItemsList(@PathVariable int userId, Principal principal) {
        User user = userDao.getUserByUsername(principal.getName());
        final List<Item> itemList = this.itemDao.getAllItems(userId);
        return new ItemResponseDTO(itemList);
    }
    @GetMapping("/itemdetails/{userId}/{itemId}")
    public Item getItem(@PathVariable int userId, @PathVariable int itemId, Principal principal) {
        User user = userDao.getUserByUsername(principal.getName());
        return itemDao.getItem(itemId, user.getId());
    }
}
