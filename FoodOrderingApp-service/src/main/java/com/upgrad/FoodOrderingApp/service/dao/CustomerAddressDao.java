package com.upgrad.FoodOrderingApp.service.dao;

import com.upgrad.FoodOrderingApp.service.entity.AddressEntity;
import com.upgrad.FoodOrderingApp.service.entity.CustomerEntity;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;

@Repository
public class CustomerAddressDao {

    @PersistenceContext
    private EntityManager entityManager;


    public AddressEntity getAddress(String customerId) {
        try {
            return entityManager.createNamedQuery("addressIdByCustomer", AddressEntity.class).setParameter("address", customerId).getSingleResult();
        } catch (NoResultException nre) {
            return null;
        }
    }

//    public CustomerEntity getCustomer(String addressId) {
//        try {
//            return entityManager.createNamedQuery("customerIdByAddress", AddressEntity.class).setParameter("uuid", customerId).getSingleResult();
//        } catch (NoResultException nre) {
//            return null;
//        }
//    }
}
