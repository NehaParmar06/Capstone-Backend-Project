package com.upgrad.FoodOrderingApp.api.controller;

import com.upgrad.FoodOrderingApp.api.model.SaveAddressRequest;
import com.upgrad.FoodOrderingApp.api.model.SaveAddressResponse;
import com.upgrad.FoodOrderingApp.api.model.StatesList;
import com.upgrad.FoodOrderingApp.api.model.StatesListResponse;
import com.upgrad.FoodOrderingApp.service.businness.AddressService;
import com.upgrad.FoodOrderingApp.service.businness.StateService;
import com.upgrad.FoodOrderingApp.service.entity.AddressEntity;
import com.upgrad.FoodOrderingApp.service.entity.CustomerAuthEntity;
import com.upgrad.FoodOrderingApp.service.entity.StateEntity;
import com.upgrad.FoodOrderingApp.service.exception.SignUpRestrictedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


@RestController
@CrossOrigin
public class AddressController {

    @Autowired
    private AddressService addressService;

    @Autowired
    private StateService stateService;

    @RequestMapping(method = RequestMethod.POST, path = "/address", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<SaveAddressResponse> saveAddress(final SaveAddressRequest saveAddressRequest) throws SignUpRestrictedException {
        try {
            final AddressEntity addressEntity = new AddressEntity();
            addressEntity.setUuid(UUID.randomUUID().toString());
            addressEntity.setUuid(saveAddressRequest.getStateUuid());
            addressEntity.setFlatBuildNumber(saveAddressRequest.getFlatBuildingName());
            addressEntity.setLocality(saveAddressRequest.getLocality());
            addressEntity.setCity(saveAddressRequest.getCity());
            addressEntity.setPincode(saveAddressRequest.getPincode());
            //        addressEntity.setState_id(saveAddressRequest.getStateUuid());
            addressEntity.setActive(1);


            CustomerAuthEntity customerAuthEntity = new CustomerAuthEntity();

            final AddressEntity createdAddressEntity = addressService.saveAddress(addressEntity, customerAuthEntity.getAccessToken());
            SaveAddressResponse saveAddressResponse = new SaveAddressResponse().id(createdAddressEntity.getUuid()).status("ADDRESS SUCCESSFULLY REGISTERED");
            return new ResponseEntity<SaveAddressResponse>(saveAddressResponse, HttpStatus.CREATED);
        } catch (Exception e) {
            System.out.println(e);
            return null;
        }

    }

    @RequestMapping(method = RequestMethod.GET, path = "/states", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<List<StatesListResponse>> getAllQuestions() {
        List<StateEntity> stateEntities = stateService.getAllStates();
        List<StatesListResponse> stateResponseList = new ArrayList<StatesListResponse>();
        for (int i = 0; i < stateEntities.size(); i++) {
            StateEntity stateEntity = stateEntities.get(i);
            //stateResponseList.add(new StatesListResponse().states(stateEntity.getState_name()).id(stateEntity.getUuid()));
            //stateResponseList.add(stateEntity);
        }
        return new ResponseEntity<List<StatesListResponse>>(stateResponseList, HttpStatus.OK);
    }


}
