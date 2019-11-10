package com.upgrad.FoodOrderingApp.service.dao;

import com.upgrad.FoodOrderingApp.service.entity.AddressEntity;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
public class AddressDao {
    @PersistenceContext
    private EntityManager entityManager;

    public AddressEntity saveAddress(AddressEntity addressEntity) {
        entityManager.persist(addressEntity);
        return addressEntity;
    }


    public List<AddressEntity> getAllSavedAddress(String customerId) {
        try {
            return entityManager.createNamedQuery("allSavedAddress", AddressEntity.class).setParameter("uuid", customerId).getResultList();
        } catch (NoResultException nre) {
            return null;
        }
    }

    public AddressEntity getAddress(String customerId) {
        try {
            return entityManager.createNamedQuery("savedAddress", AddressEntity.class).setParameter("uuid", customerId).getSingleResult();
        } catch (NoResultException nre) {
            return null;
        }
    }

    public AddressEntity deleteAddress(final AddressEntity addressEntity) {
        entityManager.remove(addressEntity);
        return addressEntity;
    }

}
