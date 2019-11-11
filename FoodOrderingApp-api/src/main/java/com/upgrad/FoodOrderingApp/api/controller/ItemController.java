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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/")
public class ItemController {

    @Autowired
    RestaurantBusinessService restaurantBusinessService;

    @Autowired
    CategoryService categoryService;

    @Autowired
    ItemService itemService;

    @RequestMapping(method = RequestMethod.GET, path = "/item/restaurant/{restaurant_id}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<RestaurantDetailsResponse> getPopularItems(@PathVariable("restaurant_id") String restaurant_id) throws RestaurantNotFoundException {
        if ( null == restaurant_id){
            throw new RestaurantNotFoundException("RNF-001", "No restaurant by this id");
        }
        Restaurant restaurant = restaurantBusinessService.getRestaurantById(restaurant_id);



        return new ResponseEntity<>(null, HttpStatus.OK);
    }
}


