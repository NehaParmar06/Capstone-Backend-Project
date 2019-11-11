package com.upgrad.FoodOrderingApp.service.businness;

import com.upgrad.FoodOrderingApp.service.dao.AddressDao;
import com.upgrad.FoodOrderingApp.service.dao.CustomerAuthTokenDao;
import com.upgrad.FoodOrderingApp.service.entity.AddressEntity;
import com.upgrad.FoodOrderingApp.service.entity.CustomerAuthEntity;
import com.upgrad.FoodOrderingApp.service.entity.CustomerEntity;
import com.upgrad.FoodOrderingApp.service.exception.AddressNotFoundException;
import com.upgrad.FoodOrderingApp.service.exception.AuthorizationFailedException;
import com.upgrad.FoodOrderingApp.service.exception.SaveAddressException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.UUID;

@Service
public class AddressService {

    @Autowired
    private CustomerAuthTokenDao customerAuthTokenDao;

    @Autowired
    private AddressDao addressDao;

    @Autowired
    private CustomerService customerService;

    @Transactional(propagation = Propagation.REQUIRED)
    public AddressEntity saveAddress(AddressEntity addressEntity, final String accessToken) throws AuthorizationFailedException,
            SaveAddressException, AddressNotFoundException {

        CustomerAuthEntity customerAuthEntity = customerAuthTokenDao.getCustomerAuthToken(accessToken);
        if(customerAuthEntity == null){
            throw new AuthorizationFailedException("ATHR-001", "Customer is not Logged in.");
        }
        else if(customerAuthEntity != null && addressEntity.getActive() == 0){
            throw new AuthorizationFailedException("ATHR-002", "Customer is logged out. Log in again to access this endpoint.");
        }
        else if(customerAuthEntity != null && customerAuthEntity.getExpires_at().isBefore(ZonedDateTime.now())){
            throw new AuthorizationFailedException("ATHR-003", "Your session is expired. Log in again to access this endpoint.");
        }
        else if(addressEntity.getUuid() == null || addressEntity.getFlatBuildNumber() == null || addressEntity.getCity() == null
                || addressEntity.getLocality() == null || addressEntity.getPincode() == null){
            throw new SaveAddressException("SAR-001", "No field can be empty");
        }
//        else if(addressEntity.getState_id() == null){
//            throw new AddressNotFoundException("ANF-002", "No state by this id");
//        }

        return addressDao.saveAddress(addressEntity);
    }
    @Transactional(propagation = Propagation.REQUIRED)
    public List<AddressEntity> getAllSavedAddress(final String authorization) throws AuthorizationFailedException {
        CustomerEntity customerEntity = customerService.getCustomer(authorization);
        return addressDao.getAllSavedAddress(customerEntity.getUuid());
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public AddressEntity deleteSavedAddress(final String authorization, final UUID addressId) throws AuthorizationFailedException, AddressNotFoundException {
        CustomerEntity customerEntity = customerService.getCustomer(authorization);
        AddressEntity addressEntity = addressDao.getAddress(authorization);
//        if(customerEntity.getUuid() == addressEntity.getCust){
////
////        }
        if(addressId == null){
            throw new AddressNotFoundException("ANF-005", "Address id can not be empty");
        }
        if(addressEntity == null){
            throw new AddressNotFoundException("ANF-003", "No address by this id");
        }

        return addressDao.deleteAddress(addressEntity);
    }


}
