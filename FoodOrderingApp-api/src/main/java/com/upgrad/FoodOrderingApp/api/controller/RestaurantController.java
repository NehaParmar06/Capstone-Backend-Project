package com.upgrad.FoodOrderingApp.api.controller;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.upgrad.FoodOrderingApp.api.model.*;
import com.upgrad.FoodOrderingApp.service.businness.CategoryService;
import com.upgrad.FoodOrderingApp.service.businness.ItemService;
import com.upgrad.FoodOrderingApp.service.businness.RestaurantBusinessService;
import com.upgrad.FoodOrderingApp.service.entity.*;
import com.upgrad.FoodOrderingApp.service.exception.CategoryNotFoundException;
import com.upgrad.FoodOrderingApp.service.exception.RestaurantNotFoundException;
import com.upgrad.FoodOrderingApp.service.exception.SignUpRestrictedException;
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
public class RestaurantController {

    @Autowired
    RestaurantBusinessService restaurantBusinessService;

    @Autowired
    CategoryService categoryService;

    @Autowired
    ItemService itemService;

    @RequestMapping(method = RequestMethod.GET, path = "/restaurant", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<RestaurantListResponse> getAllRestaurants() {
        RestaurantListResponse restaurantListResponse = new RestaurantListResponse();
        List<Restaurant> restaurants = restaurantBusinessService.getAllRestaurants();
        for (Restaurant restaurant : restaurants) {
            RestaurantList restaurantList = new RestaurantList();

            restaurantList.setId(UUID.fromString(restaurant.getUuid()));
            restaurantList.setRestaurantName(restaurant.getRestaurant_name());
            restaurantList.setPhotoURL(restaurant.getPhoto_url());
            restaurantList.setCustomerRating(restaurant.getCustomer_rating());
            restaurantList.setAveragePrice(restaurant.getAverage_price_for_two());
            restaurantList.setNumberCustomersRated(restaurant.getNumber_of_customers_rated());

            Address address = new Address();
            address = restaurant.getAddress();
            RestaurantDetailsResponseAddress restaurantDetailsResponseAddress = new RestaurantDetailsResponseAddress();
            restaurantDetailsResponseAddress.setId(UUID.fromString(address.getUuid()));
            restaurantDetailsResponseAddress.setFlatBuildingName(address.getFlat_buil_number());
            restaurantDetailsResponseAddress.setLocality(address.getLocality());
            restaurantDetailsResponseAddress.setCity(address.getCity());
            restaurantDetailsResponseAddress.setPincode(address.getPincode());

            State state = new State();
            state = address.getState();
            RestaurantDetailsResponseAddressState responseAddressState = new RestaurantDetailsResponseAddressState();
            responseAddressState.setId(UUID.fromString(state.getUuid()));
            responseAddressState.setStateName(state.getState_name());
            restaurantDetailsResponseAddress.setState(responseAddressState);
            restaurantList.setAddress(restaurantDetailsResponseAddress);

            String category_names = categoryService.getCategory(restaurant.getId());
            restaurantList.setCategories(category_names);
            //List<Category> categories = restaurant.getCategory();
            //restaurantList.setCategories(categories.toString());
            restaurantListResponse.addRestaurantsItem(restaurantList);
        }
        return new ResponseEntity<>(restaurantListResponse, HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.GET, path = "/restaurant/name/{restaurant_name}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<RestaurantListResponse> getRestaurantsByName(@PathVariable("restaurant_name") String restaurant_name) throws RestaurantNotFoundException {
        if (null == restaurant_name) {
            throw new RestaurantNotFoundException("RNF-003", "Restaurant name field should not be empty");
        }
        RestaurantListResponse restaurantListResponse = new RestaurantListResponse();
        List<Restaurant> restaurants = restaurantBusinessService.getRestaurantsByName(restaurant_name);
        for (Restaurant restaurant : restaurants) {
            RestaurantList restaurantList = new RestaurantList();

            restaurantList.setId(UUID.fromString(restaurant.getUuid()));
            restaurantList.setRestaurantName(restaurant.getRestaurant_name());
            restaurantList.setPhotoURL(restaurant.getPhoto_url());
            restaurantList.setCustomerRating(restaurant.getCustomer_rating());
            restaurantList.setAveragePrice(restaurant.getAverage_price_for_two());
            restaurantList.setNumberCustomersRated(restaurant.getNumber_of_customers_rated());

            Address address = new Address();
            address = restaurant.getAddress();
            RestaurantDetailsResponseAddress restaurantDetailsResponseAddress = new RestaurantDetailsResponseAddress();
            restaurantDetailsResponseAddress.setId(UUID.fromString(address.getUuid()));
            restaurantDetailsResponseAddress.setFlatBuildingName(address.getFlat_buil_number());
            restaurantDetailsResponseAddress.setLocality(address.getLocality());
            restaurantDetailsResponseAddress.setCity(address.getCity());
            restaurantDetailsResponseAddress.setPincode(address.getPincode());

            State state = new State();
            state = address.getState();
            RestaurantDetailsResponseAddressState responseAddressState = new RestaurantDetailsResponseAddressState();
            responseAddressState.setId(UUID.fromString(state.getUuid()));
            responseAddressState.setStateName(state.getState_name());
            restaurantDetailsResponseAddress.setState(responseAddressState);
            restaurantList.setAddress(restaurantDetailsResponseAddress);

            String category_names = categoryService.getCategory(restaurant.getId());
            restaurantList.setCategories(category_names);
            restaurantListResponse.addRestaurantsItem(restaurantList);
        }
        return new ResponseEntity<>(restaurantListResponse, HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.GET, path = "/restaurant/category/{category_id}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<RestaurantListResponse> getRestaurantsByCategoryId(@PathVariable("category_id") String category_id) throws CategoryNotFoundException, RestaurantNotFoundException {
        RestaurantListResponse restaurantListResponse = new RestaurantListResponse();
        if (null == category_id) {
            throw new CategoryNotFoundException("CNF-001", "Category id field should not be empty");
        }

        List<RestaurantCategory> restaurantCategoryList = categoryService.getRestaurantByCategoryId(category_id);
        if (null == restaurantCategoryList || restaurantCategoryList.size() == 0) {
            return null;
        }
        List<Restaurant> restaurants = new ArrayList<>();
        for (RestaurantCategory restaurantCategory : restaurantCategoryList) {
            // Get Restaurant UUID from Dao
            Restaurant restaurant = restaurantBusinessService.getRestaurantUUIDById(restaurantCategory.getRestaurant_id());
            restaurants.add(restaurant);
        }

        for (Restaurant restaurant : restaurants) {
            RestaurantList restaurantList = new RestaurantList();

            restaurantList.setId(UUID.fromString(restaurant.getUuid()));
            restaurantList.setRestaurantName(restaurant.getRestaurant_name());
            restaurantList.setPhotoURL(restaurant.getPhoto_url());
            restaurantList.setCustomerRating(restaurant.getCustomer_rating());
            restaurantList.setAveragePrice(restaurant.getAverage_price_for_two());
            restaurantList.setNumberCustomersRated(restaurant.getNumber_of_customers_rated());

            Address address = new Address();
            address = restaurant.getAddress();
            RestaurantDetailsResponseAddress restaurantDetailsResponseAddress = new RestaurantDetailsResponseAddress();
            restaurantDetailsResponseAddress.setId(UUID.fromString(address.getUuid()));
            restaurantDetailsResponseAddress.setFlatBuildingName(address.getFlat_buil_number());
            restaurantDetailsResponseAddress.setLocality(address.getLocality());
            restaurantDetailsResponseAddress.setCity(address.getCity());
            restaurantDetailsResponseAddress.setPincode(address.getPincode());

            State state = new State();
            state = address.getState();
            RestaurantDetailsResponseAddressState responseAddressState = new RestaurantDetailsResponseAddressState();
            responseAddressState.setId(UUID.fromString(state.getUuid()));
            responseAddressState.setStateName(state.getState_name());
            restaurantDetailsResponseAddress.setState(responseAddressState);
            restaurantList.setAddress(restaurantDetailsResponseAddress);

            String category_names = categoryService.getCategory(restaurant.getId());
            restaurantList.setCategories(category_names);
            restaurantListResponse.addRestaurantsItem(restaurantList);
        }
        return new ResponseEntity<>(restaurantListResponse, HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.GET, path = "/api/restaurant/{restaurant_id}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<RestaurantDetailsResponse> getRestaurantsByRestaurantId(@PathVariable("restaurant_id") String restaurant_id) throws RestaurantNotFoundException {
        if (null == restaurant_id) {
            throw new RestaurantNotFoundException("RNF-002", "Restaurant id field should not be empty");
        }
        RestaurantDetailsResponse restaurantDetailsResponse = new RestaurantDetailsResponse();
        Restaurant restaurant = restaurantBusinessService.getRestaurantById(restaurant_id);

        restaurantDetailsResponse.setId(UUID.fromString(restaurant_id));
        restaurantDetailsResponse.setRestaurantName(restaurant.getRestaurant_name());
        restaurantDetailsResponse.setPhotoURL(restaurant.getPhoto_url());
        restaurantDetailsResponse.setCustomerRating(restaurant.getCustomer_rating());
        restaurantDetailsResponse.setAveragePrice(restaurant.getAverage_price_for_two());
        restaurantDetailsResponse.setNumberCustomersRated(restaurant.getNumber_of_customers_rated());

        Address address = new Address();
        address = restaurant.getAddress();
        RestaurantDetailsResponseAddress restaurantDetailsResponseAddress = new RestaurantDetailsResponseAddress();
        restaurantDetailsResponseAddress.setId(UUID.fromString(address.getUuid()));
        restaurantDetailsResponseAddress.setFlatBuildingName(address.getFlat_buil_number());
        restaurantDetailsResponseAddress.setLocality(address.getLocality());
        restaurantDetailsResponseAddress.setCity(address.getCity());
        restaurantDetailsResponseAddress.setPincode(address.getPincode());

        State state = new State();
        state = address.getState();
        RestaurantDetailsResponseAddressState responseAddressState = new RestaurantDetailsResponseAddressState();
        responseAddressState.setId(UUID.fromString(state.getUuid()));
        responseAddressState.setStateName(state.getState_name());
        restaurantDetailsResponseAddress.setState(responseAddressState);
        restaurantDetailsResponse.setAddress(restaurantDetailsResponseAddress);

        List<Category> categories = categoryService.getAllCategoryObject(restaurant.getId());

        //CategoryDetailsResponse categoryDetailsResponse = new CategoryDetailsResponse();
        List<CategoryList> categoryList = new ArrayList<>();
        for (Category category : categories){
            // Iterate over each category and get the item id from category item
            CategoryList list = new CategoryList();
            list.setId(UUID.fromString(category.getUuid()));
            list.setCategoryName(category.getCategory_name());

            List<CategoryItem> categoryItems = itemService.getItemsbyCategoryId(category.getId());
            List<ItemList> itemLists = new ArrayList<>();
            for(CategoryItem categoryItem: categoryItems){
                Item item = itemService.getItemsbyItemsId(categoryItem.getItem_id());
                ItemList itemList = new ItemList();
                itemList.setId(UUID.fromString(item.getUuid()));
                itemList.setItemName(item.getItem_name());
                itemList.setPrice(item.getPrice());
                //TODO : Add Veg for 0 and 1 for Non Veg for ItemTyep
                //itemList.setItemType(ItemList.ItemTypeEnum.valueOf(item.getType()));
                itemLists.add(itemList);
            }
            list.setItemList(itemLists);
            categoryList.add(list);
        }
        restaurantDetailsResponse.setCategories(categoryList);
        //List<Category> categories = restaurant.getCategory();
        //restaurantList.setCategories(categories.toString());
        return new ResponseEntity<>(restaurantDetailsResponse, HttpStatus.OK);
    }
}


