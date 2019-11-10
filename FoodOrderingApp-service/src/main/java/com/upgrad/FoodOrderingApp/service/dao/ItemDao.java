package com.upgrad.FoodOrderingApp.service.dao;

import com.upgrad.FoodOrderingApp.service.entity.CategoryItem;
import com.upgrad.FoodOrderingApp.service.entity.Item;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
public class ItemDao {

    @PersistenceContext
    private EntityManager entityManager;

    public List<CategoryItem> getItemsByCategoryId(int category_id) {
        try {
            return entityManager.createNamedQuery("getObjectByCategoryId", CategoryItem.class).setParameter("category_id", category_id).getResultList();

        } catch (NoResultException nre) {
            return null;
        }
    }

    public Item getItemsByItemId(int category_id) {
        try {
            return entityManager.createNamedQuery("getItemsByItemId", Item.class).setParameter("id", category_id).getSingleResult();

        } catch (NoResultException nre) {
            return null;
        }
    }
}
