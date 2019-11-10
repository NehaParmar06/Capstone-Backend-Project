package com.upgrad.FoodOrderingApp.api.controller;

import com.upgrad.FoodOrderingApp.api.model.*;
import com.upgrad.FoodOrderingApp.service.businness.CategoryService;
import com.upgrad.FoodOrderingApp.service.businness.ItemService;
import com.upgrad.FoodOrderingApp.service.businness.RestaurantBusinessService;
import com.upgrad.FoodOrderingApp.service.entity.*;
import com.upgrad.FoodOrderingApp.service.exception.CategoryNotFoundException;
import com.upgrad.FoodOrderingApp.service.exception.RestaurantNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/")
public class CategoryController {

    @Autowired
    CategoryService categoryService;

    @RequestMapping(method = RequestMethod.GET, path = "/category", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<CategoriesListResponse> getAllCategories() throws CategoryNotFoundException, RestaurantNotFoundException {
        CategoriesListResponse categoriesListResponse = new CategoriesListResponse();
        CategoryListResponse categoryListResponse = new CategoryListResponse();

        List<Category> categoryList = categoryService.getAllCategoryObjectNoFilter();
        for( Category category: categoryList){
            categoryListResponse.setId(UUID.fromString(category.getUuid()));
            categoryListResponse.setCategoryName(category.getCategory_name());
            categoriesListResponse.addCategoriesItem(categoryListResponse);
        }
        return new ResponseEntity<>(categoriesListResponse, HttpStatus.OK);
    }
}