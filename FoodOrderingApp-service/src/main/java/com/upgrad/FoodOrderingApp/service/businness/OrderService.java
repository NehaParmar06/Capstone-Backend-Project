package com.upgrad.FoodOrderingApp.service.businness;

import com.upgrad.FoodOrderingApp.service.dao.CouponDao;
import com.upgrad.FoodOrderingApp.service.dao.OrdersDao;
import com.upgrad.FoodOrderingApp.service.entity.*;
import com.upgrad.FoodOrderingApp.service.exception.AuthorizationFailedException;
import com.upgrad.FoodOrderingApp.service.exception.CouponNotFoundException;
import com.upgrad.FoodOrderingApp.service.exception.RestaurantNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class OrderService {

    @Autowired
    private CustomerService customerService;

    @Autowired
    private CouponDao couponDao;

    @Autowired
    private OrdersDao ordersDao;

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

    @Transactional(propagation = Propagation.REQUIRED)
    public List<Orders> getOrdersById(int restaurant_id) throws RestaurantNotFoundException {
        return ordersDao.getOrdersById(restaurant_id);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public List<OrderItem> getItemsByOrderId(int order_id) throws RestaurantNotFoundException {
        return ordersDao.getItemsByOrdersId(order_id);
    }



}
