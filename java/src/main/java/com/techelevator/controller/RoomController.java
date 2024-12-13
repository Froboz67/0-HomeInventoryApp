package com.techelevator.controller;


import com.techelevator.dao.ItemDao;
import com.techelevator.dao.RoomDao;
import com.techelevator.dao.UserDao;
import com.techelevator.model.Item;
import com.techelevator.model.Room;
import com.techelevator.model.RoomResponseDTO;
import com.techelevator.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.security.Principal;
import java.util.List;

@RestController
@CrossOrigin
@PreAuthorize("isAuthenticated()")
public class RoomController {

    @Autowired
    private RoomDao roomDao;
    @Autowired
    private UserDao userDao;
    @Autowired
    private ItemDao itemDao;

    @GetMapping("/rooms")
    public RoomResponseDTO getRooms(Principal principal) {
        String currentUser = principal.getName();
        User user = userDao.getUserByUsername(currentUser);
        final List<Room> rooms = this.roomDao.getRooms(user.getId());
        return new RoomResponseDTO(rooms);
    }
    @GetMapping("/rooms/{itemId}")
    public Room getRoom(@PathVariable int itemId, Principal principal) {
        String currentUser = principal.getName();
        User user = userDao.getUserByUsername(currentUser);
        int userId = user.getId();

        Item item = itemDao.getItem(itemId);
        if (item.getUserId() != userId) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN);
        }

       return roomDao.getRoomName(itemId);
    }

}
