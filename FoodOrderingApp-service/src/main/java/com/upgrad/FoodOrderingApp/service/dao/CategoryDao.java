package com.upgrad.FoodOrderingApp.service.dao;

import com.upgrad.FoodOrderingApp.service.entity.Category;
import com.upgrad.FoodOrderingApp.service.entity.RestaurantCategory;
import com.upgrad.FoodOrderingApp.service.exception.CategoryNotFoundException;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Repository
public class CategoryDao {

    @PersistenceContext
    private EntityManager entityManager;

    // Function accepts the restaurant id and gets the category id's
    public String getCategory(int id) {
        try {
            List<RestaurantCategory> restaurantCategories = entityManager.createNamedQuery("getByRestaurantId", RestaurantCategory.class).setParameter("restaurant_id", id).getResultList();
            List<String> category_name = new ArrayList<>();
            for (RestaurantCategory restaurantCategory : restaurantCategories) {
                // get category names from the id's fetched
                Category category = entityManager.createNamedQuery("getCategoryName", Category.class).setParameter("id", restaurantCategory.getCategory_id()).getSingleResult();
                category_name.add(category.getCategory_name());
            }
            // Sort this array alphabetically
            Collections.sort(category_name);

            // Convert to comma separated list
            String listString = String.join(", ", category_name);

            return listString;

        } catch (NoResultException nre) {
            return null;
        }
    }

    // Function accepts the restaurant id and gets the category objects
    public List<Category> getAllCategoryObject(int id) {
        try {
            List<RestaurantCategory> restaurantCategories = entityManager.createNamedQuery("getByRestaurantId", RestaurantCategory.class).setParameter("restaurant_id", id).getResultList();
            List<Category> categoryList = new ArrayList<>();
            for (RestaurantCategory restaurantCategory : restaurantCategories) {
                // get category names from the id's fetched
                Category category = entityManager.createNamedQuery("getCategoryName", Category.class).setParameter("id", restaurantCategory.getCategory_id()).getSingleResult();
                categoryList.add(category);
            }

            return categoryList;

        } catch (NoResultException nre) {
            return null;
        }
    }

    // Function accepts the category uuid and gets the category id's
    public List<RestaurantCategory> getCategoryById(String uuid) throws CategoryNotFoundException {
        try {
            // Get Id from UUOID
            Category category = entityManager.createNamedQuery("getCategoryByUUId", Category.class).setParameter("uuid", uuid).getSingleResult();
            if (null == category) {
                throw new CategoryNotFoundException("CNF-002", "No category by this id");
            }

            return entityManager.createNamedQuery("getByCategoryId", RestaurantCategory.class).setParameter("category_id", category.getId()).getResultList();

        } catch (NoResultException nre) {
            return null;
        }
    }
}
