package com.upgrad.FoodOrderingApp.service.businness;

import com.upgrad.FoodOrderingApp.service.dao.PaymentDao;
import com.upgrad.FoodOrderingApp.service.entity.CustomerEntity;
import com.upgrad.FoodOrderingApp.service.entity.PaymentEntity;
import com.upgrad.FoodOrderingApp.service.exception.AuthorizationFailedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class PaymentService {

    @Autowired
    private CustomerService customerService;

    @Autowired
    private PaymentDao paymentDao;

    @Transactional(propagation = Propagation.REQUIRED)
    public List<PaymentEntity> getPaymentMethods(final String authorization) throws AuthorizationFailedException {
        CustomerEntity customerEntity = customerService.getCustomer(authorization);
        return paymentDao.getPaymentMethods();
    }
}
