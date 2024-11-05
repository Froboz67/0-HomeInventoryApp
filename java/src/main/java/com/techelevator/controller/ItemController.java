package com.techelevator.controller;

import com.techelevator.dao.ItemDao;
import com.techelevator.dao.UserDao;
import com.techelevator.model.Item;
import com.techelevator.model.ItemResponseDTO;
import com.techelevator.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
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
    public void saveItem(@RequestBody Item item, Principal principal) {
        User user = userDao.getUserByUsername(principal.getName());
        itemDao.saveItem(item, user.getId());
    }
    @PostMapping("/update/{itemId}/{userId}")
    public Item updateItem(@PathVariable int itemId, @PathVariable int userId, @RequestBody Item item, Principal principal) {
        User user = userDao.getUserByUsername(principal.getName());
        itemDao.updateItem(item, userId);
        return new Item();
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
