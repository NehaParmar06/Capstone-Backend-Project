package com.upgrad.FoodOrderingApp.service.dao;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;

import com.upgrad.FoodOrderingApp.service.entity.CustomerAuthEntity;
import com.upgrad.FoodOrderingApp.service.entity.CustomerEntity;
import org.springframework.stereotype.Repository;

@Repository
public class CustomerDao {

    @PersistenceContext
    private EntityManager entityManager;

    public CustomerEntity createUser(CustomerEntity customerEntity) {
        entityManager.persist(customerEntity);
        return customerEntity;
    }

    public CustomerEntity getCustomerByContact(final String contact_number) {
        try {
            return entityManager.createNamedQuery("customerByContact", CustomerEntity.class).setParameter("contact_number", contact_number).getSingleResult();
        } catch (NoResultException nre) {
            return null;
        }
    }

    public CustomerEntity updateCustomer(final CustomerEntity updatedCustomerEntity) {
        return  entityManager.merge(updatedCustomerEntity);
    }

    public void updateAuthToken(final CustomerAuthEntity customerAuthTokenEntity) {
        entityManager.merge(customerAuthTokenEntity);
    }

    public CustomerAuthEntity getCustomerAuthToken(final String accesstoken) {
        try {
            return entityManager.createNamedQuery("customerAuthTokenByAccessToken", CustomerAuthEntity.class).setParameter("accessToken", accesstoken).getSingleResult();
        } catch (NoResultException nre) {
            return null;
        }
    }

}
