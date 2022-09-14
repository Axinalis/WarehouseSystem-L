package com.axinalis.store.changer;

import com.axinalis.store.data.Database;
import com.axinalis.store.data.Store;
import com.axinalis.store.data.StoreItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class DataChanger {

    private static final int maxProductNumber = 5;

    @Autowired
    private Database database;

    public List<ChangeSetItem> changeData(){
        List<ChangeSetItem> changeSet = new ArrayList<>();
        for(Store store : database.getStores()){
            for(Long category : store.getCategories()){
                for(StoreItem item : store.getStoreItemsByCategory(category)){
                    // Goes from 2% to 10% depending on category
                    if(Math.random() * 100 < database.getFrequencyOfCategory(category)){
                        long newAmount = item.getCurrentStock() - randomValue(maxProductNumber);
                        changeSet.add(new ChangeSetItem(
                                store.getStoreId(),
                                category,
                                item.getItemId(),
                                newAmount));
                        item.setCurrentStock(newAmount);
                    }
                }
            }
        }
        return changeSet;
    }

    // Returns some random value from 0 to limit, the frequency of some value is as high as it is close to 0
    private static int randomValue(int limit){
        return (int)(Math.pow(Math.random(), 2) * limit) + 1;
    }

}
