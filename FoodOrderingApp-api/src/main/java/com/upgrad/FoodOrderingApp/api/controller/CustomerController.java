package com.upgrad.FoodOrderingApp.api.controller;

import com.upgrad.FoodOrderingApp.api.model.LoginResponse;
import com.upgrad.FoodOrderingApp.service.businness.LoginAuthenticationService;
import com.upgrad.FoodOrderingApp.service.businness.SignupBusinessService;
import com.upgrad.FoodOrderingApp.service.entity.CustomerEntity;
import com.upgrad.FoodOrderingApp.service.entity.CustomerAuthEntity;
import com.upgrad.FoodOrderingApp.service.exception.AuthenticationFailedException;
import com.upgrad.FoodOrderingApp.service.exception.SignUpRestrictedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.upgrad.FoodOrderingApp.api.model.SignupCustomerResponse;
import com.upgrad.FoodOrderingApp.api.model.SignupCustomerRequest;

import java.util.Base64;
import java.util.UUID;

@RestController
@CrossOrigin
public class CustomerController {

    @Autowired
    private SignupBusinessService signupBusinessService;

    @Autowired
    private LoginAuthenticationService loginAuthenticationService;

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

    @RequestMapping(method = RequestMethod.POST, path = "/customer/login", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<LoginResponse> customerLogin(@RequestHeader("authorization") final String authorization) throws AuthenticationFailedException {
        try {
            String encodedText = authorization.split("Basic ")[1];
            byte[] decode = Base64.getDecoder().decode(encodedText);
            String decodedText = new String(decode);
            String[] decodedArray = decodedText.split(":");

            CustomerAuthEntity customerAuthToken = loginAuthenticationService.authenticateCustomer(decodedArray[0], decodedArray[1]);
            CustomerEntity customer = customerAuthToken.getCustomer();

            LoginResponse loginResponse = new LoginResponse().id(customer.getUuid())
                    .message("SIGNED IN SUCCESSFULLY");
            HttpHeaders headers = new HttpHeaders();
            headers.add("access-token", customerAuthToken.getAccessToken());
            return new ResponseEntity<LoginResponse>(loginResponse, headers, HttpStatus.OK);
        } catch (ArrayIndexOutOfBoundsException ofb) {
            System.out.println("Caught out of bound exception , Please try again");
            return null;
        }
    }

}
