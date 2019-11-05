package com.upgrad.FoodOrderingApp.api.controller;


import com.upgrad.FoodOrderingApp.service.businness.SignupBusinessService;
import com.upgrad.FoodOrderingApp.service.entity.CustomerEntity;
import com.upgrad.FoodOrderingApp.service.exception.SignUpRestrictedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.upgrad.FoodOrderingApp.api.model.SignupCustomerResponse;
import com.upgrad.FoodOrderingApp.api.model.SignupCustomerRequest;

import java.util.UUID;

@RestController
@RequestMapping("/")
@CrossOrigin
public class CustomerController {

    @Autowired
    private SignupBusinessService signupBusinessService;

    @RequestMapping(method = RequestMethod.POST, path = "/customer/signup", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<SignupCustomerResponse> customerSignup(final SignupCustomerRequest signupCustomerRequest) throws SignUpRestrictedException {

        signupBusinessService.getCustomerByContact(signupCustomerRequest.getContactNumber());

        final CustomerEntity customerEntity = new CustomerEntity();
        customerEntity.setUuid(UUID.randomUUID().toString());
        customerEntity.setFirstName(signupCustomerRequest.getFirstName());
        customerEntity.setLastName(signupCustomerRequest.getLastName());
        customerEntity.setEmail(signupCustomerRequest.getEmailAddress());
        customerEntity.setPassword(signupCustomerRequest.getPassword());
        customerEntity.setContact_number(signupCustomerRequest.getContactNumber());

        final CustomerEntity createdCustomerEntity = signupBusinessService.signup(customerEntity);
        SignupCustomerResponse customerResponse = new SignupCustomerResponse().id(createdCustomerEntity.getUuid()).status("CUSTOMER SUCCESSFULLY REGISTERED");
        return new ResponseEntity<SignupCustomerResponse>(customerResponse, HttpStatus.CREATED);
    }

}
