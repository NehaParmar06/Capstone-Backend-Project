package com.upgrad.FoodOrderingApp.service.businness;

import com.upgrad.FoodOrderingApp.service.dao.StateDao;
import com.upgrad.FoodOrderingApp.service.entity.StateEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class StateService {

    @Autowired
    private StateDao stateDao;

    @Transactional(propagation = Propagation.REQUIRED)
    public List<StateEntity> getAllStates() {
            return stateDao.getAllStates();
        }
}


