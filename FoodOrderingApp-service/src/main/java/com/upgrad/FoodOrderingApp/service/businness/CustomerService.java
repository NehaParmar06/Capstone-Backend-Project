package com.upgrad.FoodOrderingApp.service.businness;

import com.upgrad.FoodOrderingApp.service.dao.CustomerAuthTokenDao;
import com.upgrad.FoodOrderingApp.service.dao.CustomerDao;
import com.upgrad.FoodOrderingApp.service.entity.CustomerAuthEntity;
import com.upgrad.FoodOrderingApp.service.entity.CustomerEntity;
import com.upgrad.FoodOrderingApp.service.exception.AuthorizationFailedException;
import com.upgrad.FoodOrderingApp.service.exception.UpdateCustomerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.time.ZonedDateTime;

@Service
public class CustomerService {

    @Autowired
    private CustomerDao customerDao;

    @Autowired
    private CustomerAuthTokenDao customerAuthTokenDao;

    @Transactional(propagation = Propagation.REQUIRED)
    public CustomerEntity updateCustomerDetails(final String authorization, final String firstName, final String lastName) throws AuthorizationFailedException, UpdateCustomerException {
        if (firstName == null) {
            throw new UpdateCustomerException("UCR-002", "First name field should not be empty");
        }
        CustomerEntity customerEntity = getCustomer(authorization);
        customerEntity.setFirstName(firstName);
        customerEntity.setLastName(lastName);
        return customerDao.updateCustomer(customerEntity);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public CustomerEntity updatePassword(final String authorization, final String oldPassword, final String newPassword) throws AuthorizationFailedException, UpdateCustomerException {
        if (oldPassword == null || newPassword == null) {
            throw new UpdateCustomerException("UCR-003", "No field should be empty");
        }
        CustomerEntity customerEntity = getCustomer(authorization);
        String passwordRegex = "^(?=.*?[A-Z])(?=.*?[0-9])(?=.*?[#@$%&*!^]).{8,}$";
        if(!newPassword.matches(passwordRegex)){
            throw new UpdateCustomerException("UCR-001", "Weak password!");
        }
        if(oldPassword != customerEntity.getPassword()){
            throw new UpdateCustomerException("UCR-004", "Incorrect old password!");
        }
        customerEntity.setPassword(newPassword);
        return customerDao.updateCustomer(customerEntity);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public CustomerEntity getCustomer(final String authorization) throws AuthorizationFailedException {
        CustomerAuthEntity customerAuthTokenEntity = customerAuthTokenDao.getCustomerAuthToken(authorization);
        if (customerAuthTokenEntity == null) {
            throw new AuthorizationFailedException("ATHR-001", "Customer is not Logged in.");
        } else {
            ZonedDateTime expiryTime = customerAuthTokenEntity.getExpires_at();
            ZonedDateTime logoutTime = customerAuthTokenEntity.getLogout_at();
            ZonedDateTime nowTime = ZonedDateTime.now();
            if (nowTime.compareTo(expiryTime) > 0)
                throw new AuthorizationFailedException("ATHR-003", "Your session is expired. Log in again to access this endpoint.");
            if (logoutTime != null) {
                if (nowTime.compareTo(logoutTime) > 0)
                    throw new AuthorizationFailedException("ATHR-002", "Customer is logged out. Log in again to access this endpoint.");
            }

            CustomerEntity customerEntity = customerAuthTokenEntity.getCustomer();

            return customerEntity;

        }

    }


}
