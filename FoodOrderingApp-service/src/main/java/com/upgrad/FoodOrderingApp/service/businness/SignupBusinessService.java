package com.upgrad.FoodOrderingApp.service.businness;

import com.upgrad.FoodOrderingApp.service.exception.SignUpRestrictedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.upgrad.FoodOrderingApp.service.entity.CustomerEntity;
import com.upgrad.FoodOrderingApp.service.dao.CustomerDao;

@Service
public class SignupBusinessService {

    @Autowired
    private CustomerDao customerDao;

    @Autowired
    private PasswordCryptographyProvider passwordCryptographyProvider;

    @Transactional(propagation = Propagation.REQUIRED)
    public CustomerEntity signup(CustomerEntity customerEntity) throws SignUpRestrictedException{

        if(customerEntity.getFirstName() == null || customerEntity.getEmail() == null || customerEntity.getPassword() == null ||
                customerEntity.getContact_number() == null){
            throw new SignUpRestrictedException("SGR-005", "Except last name all fields should be filled");
        }

        String numRegex = "^\\d{10}$";
        String emailRegex = "[a-zA-Z0-9_.]+@[a-zA-Z.]+?\\.[a-zA-Z]{2,3}";
        String passwordRegex = "^(?=.*?[A-Z])(?=.*?[0-9])(?=.*?[#@$%&*!^]).{8,}$";
        if(!customerEntity.getEmail().matches(emailRegex)){
            throw new SignUpRestrictedException("SGR-002", "Invalid email-id format!");
        }
        if(!customerEntity.getContact_number().matches(numRegex)){
            throw new SignUpRestrictedException("SGR-003", "Invalid contact number!");
        }
        if(!customerEntity.getPassword().matches(passwordRegex)){
            throw new SignUpRestrictedException("SGR-004", "Weak password!");
        }

        String[] encryptedText = passwordCryptographyProvider.encrypt(customerEntity.getPassword());
        customerEntity.setSalt(encryptedText[0]);
        customerEntity.setPassword(encryptedText[1]);

        return customerDao.createUser(customerEntity);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public CustomerEntity getCustomerByContact(final String contactNumber) throws SignUpRestrictedException {
        CustomerEntity customerEntity = customerDao.getCustomerByContact(contactNumber);
        if (customerEntity != null) {
            throw new SignUpRestrictedException("SGR-001", "This contact number is already registered! Try other contact number.");
        }
        return customerEntity;
    }

//    @Transactional(propagation = Propagation.REQUIRED)
//    public CustomerEntity getUserByEmail(final String emailAddress) throws SignUpRestrictedException {
//        CustomerEntity customerEntity = customerDao.getUserByEmail(emailAddress);
//        if (customerEntity != null) {
//            throw new SignUpRestrictedException("SGR-002", "This user has already been registered, try with any other emailId");
//        }
//        return customerEntity;
//    }



}
