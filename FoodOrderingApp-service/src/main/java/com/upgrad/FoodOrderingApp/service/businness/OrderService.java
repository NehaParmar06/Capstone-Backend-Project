package com.upgrad.FoodOrderingApp.service.businness;

import com.upgrad.FoodOrderingApp.service.dao.CouponDao;
import com.upgrad.FoodOrderingApp.service.entity.CouponEntity;
import com.upgrad.FoodOrderingApp.service.entity.CustomerEntity;
import com.upgrad.FoodOrderingApp.service.exception.AuthorizationFailedException;
import com.upgrad.FoodOrderingApp.service.exception.CouponNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class OrderService {

    @Autowired
    private CustomerService customerService;

    @Autowired
    private CouponDao couponDao;

    @Transactional(propagation = Propagation.REQUIRED)
    public CouponEntity getCouponDetailsByName(final String authorization, final String couponName) throws AuthorizationFailedException, CouponNotFoundException {
        CustomerEntity customerEntity = customerService.getCustomer(authorization);
        if (couponName.isEmpty())
            throw new CouponNotFoundException("CPF-002", "Coupon name field should not be empty");
        CouponEntity couponEntity = couponDao.getCouponByName(couponName);
        if (couponEntity == null)
            throw new CouponNotFoundException("CPF-001", "No coupon by this name");
        else
            return couponEntity;
    }

}
