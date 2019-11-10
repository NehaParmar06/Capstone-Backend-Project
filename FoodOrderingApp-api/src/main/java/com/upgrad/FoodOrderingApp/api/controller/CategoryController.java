package com.upgrad.FoodOrderingApp.api.controller;

import com.upgrad.FoodOrderingApp.api.model.*;
import com.upgrad.FoodOrderingApp.service.businness.CategoryService;
import com.upgrad.FoodOrderingApp.service.businness.ItemService;
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
public class CategoryController {

    @Autowired
    CategoryService categoryService;

    @Autowired
    ItemService itemService;

    @RequestMapping(method = RequestMethod.GET, path = "/category", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<CategoriesListResponse> getAllCategories() throws CategoryNotFoundException, RestaurantNotFoundException {
        CategoriesListResponse categoriesListResponse = new CategoriesListResponse();
        CategoryListResponse categoryListResponse = new CategoryListResponse();

        List<Category> categoryList = categoryService.getAllCategoryObjectNoFilter();
        for (Category category : categoryList) {
            categoryListResponse.setId(UUID.fromString(category.getUuid()));
            categoryListResponse.setCategoryName(category.getCategory_name());
            categoriesListResponse.addCategoriesItem(categoryListResponse);
        }
        return new ResponseEntity<>(categoriesListResponse, HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.GET, path = "/category/{category_id}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<CategoryDetailsResponse> getRestaurantsByName(@PathVariable("category_id") String category_id) throws CategoryNotFoundException {
        if (null == category_id) {
            throw new CategoryNotFoundException("CNF-001", "Category id field should not be empty");
        }

        Category category = categoryService.getCategoryByCategoryUUId(category_id);
        CategoryDetailsResponse categoryDetailsResponse = new CategoryDetailsResponse();

        categoryDetailsResponse.setId(UUID.fromString(category.getUuid()));
        categoryDetailsResponse.setCategoryName(category.getCategory_name());

        List<CategoryItem> categoryItems = itemService.getItemsbyCategoryId(category.getId());
        List<ItemList> itemLists = new ArrayList<>();
        for (CategoryItem categoryItem : categoryItems) {
            Item item = itemService.getItemsbyItemsId(categoryItem.getItem_id());
            ItemList itemList = new ItemList();
            itemList.setId(UUID.fromString(item.getUuid()));
            itemList.setItemName(item.getItem_name());
            itemList.setPrice(item.getPrice());
            if (Integer.parseInt(item.getType()) == 0) {
                itemList.setItemType(ItemList.ItemTypeEnum.VEG);
            }
            if (Integer.parseInt(item.getType()) == 1) {
                itemList.setItemType(ItemList.ItemTypeEnum.NON_VEG);
            }

            itemLists.add(itemList);
        }
        categoryDetailsResponse.setItemList(itemLists);

        return new ResponseEntity<>(categoryDetailsResponse, HttpStatus.OK);
    }
}