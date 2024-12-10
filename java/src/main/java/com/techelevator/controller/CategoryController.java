package com.techelevator.controller;


import com.techelevator.dao.CategoryDao;
import com.techelevator.dao.UserDao;
import com.techelevator.model.CategoryResoponseDTO;
import com.techelevator.model.User;
import com.techelevator.model.Category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.List;

@RestController
@CrossOrigin
@PreAuthorize("isAuthenticated()")
public class CategoryController {

    @Autowired
    private CategoryDao categoryDao;
    @Autowired
    private UserDao userDao;

    @GetMapping("/categories")
    public CategoryResoponseDTO getCategories(Principal principal) {
        String currentUser = principal.getName();
        User user = userDao.getUserByUsername(currentUser);
        final List<Category> categories = this.categoryDao.getCategories(user.getId());
        return new CategoryResoponseDTO(categories);
    }


}
