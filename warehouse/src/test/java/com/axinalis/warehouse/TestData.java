package com.axinalis.warehouse;

import com.axinalis.warehouse.consumer.ChangeSetItem;

import java.util.ArrayList;
import java.util.List;

public class TestData {

    public static String getChangeSetAsJson(){
        return "[{\"storeId\":1,\"categoryId\":2,\"productId\":23,\"currentStock\":199}," +
                "{\"storeId\":1,\"categoryId\":2,\"productId\":45,\"currentStock\":25}," +
                "{\"storeId\":1,\"categoryId\":3,\"productId\":49,\"currentStock\":87}," +
                "{\"storeId\":2,\"categoryId\":2,\"productId\":19,\"currentStock\":39}]";
    }

    public static List<ChangeSetItem> getChangeSet() {
        List<ChangeSetItem> items = new ArrayList<>();
        items.add(new ChangeSetItem(1L, 2L, 23L, 199L));
        items.add(new ChangeSetItem(1L, 2L, 45L, 25L));
        items.add(new ChangeSetItem(1L, 3L, 49L, 87L));
        items.add(new ChangeSetItem(2L, 2L, 19L, 39L));
        return items;
    }

    public static String getRandomJsonString(){
        return "{\"id\":28, \"name\":\"Anton\"}";
    }
}