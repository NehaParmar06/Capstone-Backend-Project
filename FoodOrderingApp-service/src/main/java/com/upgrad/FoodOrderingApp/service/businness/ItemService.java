package com.upgrad.FoodOrderingApp.service.businness;

import com.sun.tools.javac.jvm.Items;
import com.upgrad.FoodOrderingApp.service.dao.ItemDao;
import com.upgrad.FoodOrderingApp.service.entity.CategoryItem;
import com.upgrad.FoodOrderingApp.service.entity.Item;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ItemService {

    @Autowired
    private ItemDao itemDao;

    @Transactional(propagation = Propagation.REQUIRED)
    public List<CategoryItem> getItemsbyCategoryId(int category_id) {
        return itemDao.getItemsByCategoryId(category_id);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public Item getItemsbyItemsId(int items_id) {
        return itemDao.getItemsByItemId(items_id);
    }

}
