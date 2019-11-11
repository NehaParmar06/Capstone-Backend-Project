package com.upgrad.FoodOrderingApp.api.controller;

import com.upgrad.FoodOrderingApp.api.model.CouponDetailsResponse;
import com.upgrad.FoodOrderingApp.service.businness.OrderService;
import com.upgrad.FoodOrderingApp.service.entity.CouponEntity;
import com.upgrad.FoodOrderingApp.service.exception.AuthorizationFailedException;
import com.upgrad.FoodOrderingApp.service.exception.CouponNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8_VALUE;
import static org.springframework.web.bind.annotation.RequestMethod.GET;

@RestController
@CrossOrigin
@RequestMapping("/")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @RequestMapping(method = GET, path = "/order/coupon/{coupon_name}", produces = APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<CouponDetailsResponse> getCouponByName(@RequestHeader("authorization") String accessToken, @PathVariable("coupon_name") final String couponName) throws AuthorizationFailedException, CouponNotFoundException {
        CouponEntity couponEntity = orderService.getCouponDetailsByName(accessToken, couponName);
        final CouponDetailsResponse couponDetailsResponse = new CouponDetailsResponse().id(couponEntity.getUuid()).couponName(couponName).percent(couponEntity.getPercent());
        return new ResponseEntity<CouponDetailsResponse>(couponDetailsResponse, HttpStatus.OK);
    }
}