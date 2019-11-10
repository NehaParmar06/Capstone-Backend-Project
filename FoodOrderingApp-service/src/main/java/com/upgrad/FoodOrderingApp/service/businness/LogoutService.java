package com.upgrad.FoodOrderingApp.service.businness;


import com.upgrad.FoodOrderingApp.service.dao.CustomerAuthTokenDao;
import com.upgrad.FoodOrderingApp.service.dao.CustomerDao;
import com.upgrad.FoodOrderingApp.service.entity.CustomerAuthEntity;
import com.upgrad.FoodOrderingApp.service.exception.AuthorizationFailedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.time.ZonedDateTime;

@Service
public class LogoutService {

    @Autowired
    private CustomerDao customerDao;

    @Autowired
    private CustomerAuthTokenDao customerAuthTokenDao;

    @Transactional(propagation = Propagation.REQUIRED)
    public CustomerAuthEntity logout(final String authorization) throws AuthorizationFailedException {

        CustomerAuthEntity customerAuthEntity = customerAuthTokenDao.getCustomerAuthToken(authorization);
        if (customerAuthEntity == null) {
            throw new AuthorizationFailedException("ATHR-001", "Customer is not Logged in.");
        }
        else if(customerAuthEntity != null && customerAuthEntity.getLogout_at() != null){
            throw new AuthorizationFailedException("ATHR-002", "Customer is logged out. Log in again to access this endpoint.");
        }
        else if(customerAuthEntity != null && customerAuthEntity.getExpires_at().isBefore(ZonedDateTime.now())){
            throw new AuthorizationFailedException("ATHR-003", "Your session is expired. Log in again to access this endpoint.");
        }
        customerAuthEntity.setLogout_at(ZonedDateTime.now());
        customerDao.updateAuthToken(customerAuthEntity);

        return customerAuthEntity;
    }

}
