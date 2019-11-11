package com.upgrad.FoodOrderingApp.service.businness;

import com.upgrad.FoodOrderingApp.service.dao.CustomerAuthTokenDao;
import com.upgrad.FoodOrderingApp.service.dao.CustomerDao;
import com.upgrad.FoodOrderingApp.service.entity.CustomerAuthEntity;
import com.upgrad.FoodOrderingApp.service.entity.CustomerEntity;
import com.upgrad.FoodOrderingApp.service.exception.AuthenticationFailedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.time.ZonedDateTime;

@Service
public class LoginAuthenticationService {

    @Autowired
    private CustomerDao customerDao;

    @Autowired
    private PasswordCryptographyProvider CryptographyProvider;

    @Autowired
    private CustomerAuthTokenDao customerAuthTokenDao;

    @Transactional(propagation = Propagation.REQUIRED)
    public CustomerAuthEntity authenticateCustomer(final String contactNumber, final String password) throws AuthenticationFailedException {
        CustomerEntity customerEntity = customerDao.getCustomerByContact(contactNumber);
        if (customerEntity == null) {
            throw new AuthenticationFailedException("ATH-001", "This contact number has not been registered!");
        }

        final String encryptedPassword = CryptographyProvider.encrypt(password, customerEntity.getSalt());
        if (encryptedPassword.equals(customerEntity.getPassword())) {
            JwtTokenProvider jwtTokenProvider = new JwtTokenProvider(encryptedPassword);
            CustomerAuthEntity customerAuthTokenEntity = new CustomerAuthEntity();

            customerAuthTokenEntity.setUuid(customerEntity.getUuid());
            final ZonedDateTime now = ZonedDateTime.now();

            final ZonedDateTime expiresAt = now.plusHours(8);

            customerAuthTokenEntity.setAccessToken(jwtTokenProvider.generateToken(customerEntity.getUuid(), now, expiresAt));

            customerAuthTokenEntity.setLogin_at(now);
            customerAuthTokenEntity.setExpires_at(expiresAt);
            customerAuthTokenEntity.setCustomer(customerEntity);
            // Setting log out value to null to depict that the user is not logged out.
            customerAuthTokenEntity.setLogout_at(null);
            customerAuthTokenDao.createAuthToken(customerAuthTokenEntity);

            customerDao.updateCustomer(customerEntity);
            return customerAuthTokenEntity;
        } else {
            throw new AuthenticationFailedException("ATH-002", "Invalid Credentials");
        }
    }
}



