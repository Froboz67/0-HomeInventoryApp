package com.techelevator.controller;

import com.techelevator.dao.ItemDao;
import com.techelevator.dao.UserDao;
import com.techelevator.model.Item;
import com.techelevator.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
@CrossOrigin
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
}
