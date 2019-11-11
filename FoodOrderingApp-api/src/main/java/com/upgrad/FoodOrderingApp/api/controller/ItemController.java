package com.upgrad.FoodOrderingApp.api.controller;

import com.upgrad.FoodOrderingApp.api.model.*;
import com.upgrad.FoodOrderingApp.service.businness.CategoryService;
import com.upgrad.FoodOrderingApp.service.businness.ItemService;
import com.upgrad.FoodOrderingApp.service.businness.OrderService;
import com.upgrad.FoodOrderingApp.service.businness.RestaurantBusinessService;
import com.upgrad.FoodOrderingApp.service.entity.*;
import com.upgrad.FoodOrderingApp.service.exception.CategoryNotFoundException;
import com.upgrad.FoodOrderingApp.service.exception.RestaurantNotFoundException;
import org.hibernate.criterion.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

@RestController
@RequestMapping("/")
public class ItemController {

    @Autowired
    RestaurantBusinessService restaurantBusinessService;

    @Autowired
    CategoryService categoryService;

    @Autowired
    ItemService itemService;

    @Autowired
    OrderService orderService;

    @RequestMapping(method = RequestMethod.GET, path = "/item/restaurant/{restaurant_id}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<RestaurantDetailsResponse> getPopularItems(@PathVariable("restaurant_id") String restaurant_id) throws RestaurantNotFoundException {
        if ( null == restaurant_id){
            throw new RestaurantNotFoundException("RNF-001", "No restaurant by this id");
        }
        Map<Integer, Integer> map = new HashMap<>();
        Restaurant restaurant = restaurantBusinessService.getRestaurantById(restaurant_id);
        //Get All orders for this restaurant id
        List<Orders> ordersForRestaurant = orderService.getOrdersById(restaurant.getId());
        // For each order , get the items Ordered
        for(Orders orders: ordersForRestaurant) {
            List<OrderItem> order_item_list = orderService.getItemsByOrderId(orders.getId());
            //Get item list in each order
            for (OrderItem orderItem: order_item_list){
                Item item = itemService.getItemsbyItemsId(orderItem.getItem_id());
                // Create a map of Item name vs count
                int count = map.getOrDefault(item.getId(), 0);
                map.put(item.getId(), count + 1);
            }
        }

        Object[] a = map.entrySet().toArray();
        Arrays.sort(a, new Comparator() {
            public int compare(Object o1, Object o2) {
                return ((Map.Entry<Integer, Integer>) o2).getValue()
                        .compareTo(((Map.Entry<Integer, Integer>) o1).getValue());
            }
        });
        for (Object e : a) {
            for ( int i = 0 ; i < 4 ; i++){
                // Get Top 5 items:
                Item item = itemService.getItemsbyItemsId(((Map.Entry<Integer, Integer>) e).getKey());
                //System.out.println(((Map.Entry<String, Integer>) e).getKey() + " : " + ((Map.Entry<String, Integer>) e).getValue());

            }

        }

        return new ResponseEntity<>(null, HttpStatus.OK);
    }
}


