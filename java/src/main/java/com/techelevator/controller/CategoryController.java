package com.techelevator.controller;


import com.techelevator.dao.ItemCategoryDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin
@PreAuthorize("isAuthenticated()")
public class CategoryController {

//    @Autowired
//    private ItemCategoryDao itemCategoryDao;



}
