package com.upgrad.FoodOrderingApp.service.dao;

import com.upgrad.FoodOrderingApp.service.entity.Category;
import com.upgrad.FoodOrderingApp.service.entity.RestaurantCategory;
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
            List<RestaurantCategory> restaurantCategories = entityManager.createNamedQuery("restaurantCategoriesIds", RestaurantCategory.class).setParameter("id", id).getResultList();
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
}
