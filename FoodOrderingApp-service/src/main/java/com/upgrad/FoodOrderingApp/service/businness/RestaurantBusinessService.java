package com.upgrad.FoodOrderingApp.service.businness;

import com.upgrad.FoodOrderingApp.service.dao.RestaurantDao;
import com.upgrad.FoodOrderingApp.service.entity.Restaurant;
import com.upgrad.FoodOrderingApp.service.exception.RestaurantNotFoundException;
import com.upgrad.FoodOrderingApp.service.exception.SignUpRestrictedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class RestaurantBusinessService {

    @Autowired
    private RestaurantDao restaurantDao;

    @Transactional(propagation = Propagation.REQUIRED)
    public List<Restaurant> getAllRestaurants() {
        return restaurantDao.getAllRestaurants();
    }


    @Transactional(propagation = Propagation.REQUIRED)
    public List<Restaurant> getRestaurantsByName(String restaurant_name) throws RestaurantNotFoundException {
        if ( null == restaurant_name){
            throw new RestaurantNotFoundException("RNF-003", "Restaurant name field should not be empty");
        }
        return restaurantDao.getRestaurantsByName(restaurant_name);
    }
}
