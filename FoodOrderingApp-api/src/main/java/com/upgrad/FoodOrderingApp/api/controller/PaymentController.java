package com.upgrad.FoodOrderingApp.api.controller;

import com.upgrad.FoodOrderingApp.api.model.PaymentResponse;
import com.upgrad.FoodOrderingApp.service.businness.PaymentService;
import com.upgrad.FoodOrderingApp.service.entity.PaymentEntity;
import com.upgrad.FoodOrderingApp.service.exception.AuthorizationFailedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8_VALUE;
import static org.springframework.web.bind.annotation.RequestMethod.GET;

@RestController
@CrossOrigin
@RequestMapping("/")
public class PaymentController {

    @Autowired
    private PaymentService paymentService;

    @RequestMapping(method = GET, path = "/payment", produces = APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<List<PaymentResponse>> getPaymentMethods(@RequestHeader("authorization") String accessToken) throws AuthorizationFailedException {
        List<PaymentEntity> paymentEntities = paymentService.getPaymentMethods(accessToken);
        List<PaymentResponse> paymentListResponseArrayList = new ArrayList<>();
        for (int i = 0; i < paymentEntities.size(); i++) {
            PaymentEntity paymentEntity = paymentEntities.get(i);
            paymentListResponseArrayList.add(new PaymentResponse().id(paymentEntity.getUuid()).paymentName(paymentEntity.getPaymentName()));
        }
        return new ResponseEntity<List<PaymentResponse>>(paymentListResponseArrayList, HttpStatus.OK);
    }
}