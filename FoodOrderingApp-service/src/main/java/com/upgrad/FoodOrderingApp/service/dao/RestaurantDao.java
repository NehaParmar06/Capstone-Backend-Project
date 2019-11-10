package com.upgrad.FoodOrderingApp.service.dao;

import com.upgrad.FoodOrderingApp.service.entity.CustomerEntity;
import com.upgrad.FoodOrderingApp.service.entity.Restaurant;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
public class RestaurantDao {

    @PersistenceContext
    private EntityManager entityManager;

    public List<Restaurant> getAllRestaurants() {
        try {
            return entityManager.createQuery("SELECT p from Restaurant p order by p.customer_rating desc", Restaurant.class).getResultList();
        } catch (NoResultException nre) {
            return null;
        }
    }

    public List<Restaurant> getRestaurantsByName(String restaurant_name) {
        try {
            return entityManager.createNamedQuery("restaurantByName", Restaurant.class).setParameter("restaurant_name", restaurant_name).getResultList();
        } catch (NoResultException nre) {
            return null;
        }
    }
}
