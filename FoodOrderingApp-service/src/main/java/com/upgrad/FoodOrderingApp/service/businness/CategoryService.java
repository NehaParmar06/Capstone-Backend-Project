package com.upgrad.FoodOrderingApp.service.businness;

import com.upgrad.FoodOrderingApp.service.dao.CategoryDao;
import com.upgrad.FoodOrderingApp.service.entity.Category;
import com.upgrad.FoodOrderingApp.service.entity.RestaurantCategory;
import com.upgrad.FoodOrderingApp.service.exception.CategoryNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CategoryService {

    @Autowired
    private CategoryDao categoryDao;

    @Transactional(propagation = Propagation.REQUIRED)
    public String getCategory(int restaurant_id) {
        return categoryDao.getCategory(restaurant_id);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public List<Category> getAllCategoryObject(int restaurant_id) {
        return categoryDao.getAllCategoryObject(restaurant_id);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public List<Category> getAllCategoryObjectNoFilter() {
        return categoryDao.getAllCategoryObjectWithoutFilter();
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public List<RestaurantCategory> getRestaurantByCategoryId(String category_uuid) throws CategoryNotFoundException {
        return categoryDao.getCategoryById(category_uuid);
    }
}
