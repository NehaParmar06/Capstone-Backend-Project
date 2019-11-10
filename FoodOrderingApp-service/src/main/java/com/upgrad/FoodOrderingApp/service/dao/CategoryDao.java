package com.upgrad.FoodOrderingApp.service.dao;

import com.upgrad.FoodOrderingApp.service.entity.Category;
import com.upgrad.FoodOrderingApp.service.entity.Restaurant;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.UUID;

@Repository
public class CategoryDao {

    @PersistenceContext
    private EntityManager entityManager;

    public List<Category> getCategory(String uuid) {
        try {
            return entityManager.createNamedQuery("restaurantCategories", Category.class).setParameter("uuid", uuid).getResultList();
        } catch (NoResultException nre) {
            return null;
        }
    }
}
