package com.upgrad.FoodOrderingApp.service.dao;

import com.upgrad.FoodOrderingApp.service.entity.OrderItem;
import com.upgrad.FoodOrderingApp.service.entity.Orders;
import com.upgrad.FoodOrderingApp.service.entity.Restaurant;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
public class OrdersDao {

    @PersistenceContext
    private EntityManager entityManager;

    public List<Orders> getOrdersById(int restaurant_id) {
        try {
            return entityManager.createNamedQuery("getOrdersByRestaurantId", Orders.class).setParameter("restaurant_id", restaurant_id).getResultList();
        } catch (NoResultException nre) {
            return null;
        }
    }

    public List<OrderItem> getItemsByOrdersId(int order_id) {
        try {
            return entityManager.createNamedQuery("getOrderItemByOrderId", OrderItem.class).setParameter("order_id", order_id).getResultList();
        } catch (NoResultException nre) {
            return null;
        }
    }

}
