package com.axinalis.store;

import com.axinalis.store.changer.ChangeSetItem;
import com.axinalis.store.data.FullProductId;
import com.axinalis.store.data.ProductEntity;

import java.util.ArrayList;
import java.util.List;

public class TestData {

    public static String getChangeSetAsJson(){
        return "[{\"storeId\":1,\"categoryId\":2,\"productId\":23,\"currentStock\":199}," +
            "{\"storeId\":1,\"categoryId\":2,\"productId\":45,\"currentStock\":25}," +
            "{\"storeId\":1,\"categoryId\":3,\"productId\":49,\"currentStock\":87}," +
            "{\"storeId\":2,\"categoryId\":2,\"productId\":19,\"currentStock\":39}]";
    }

    public static List<ChangeSetItem> getChangeSet(){
        List<ChangeSetItem> items = new ArrayList<>();
        items.add(new ChangeSetItem(1L, 2L, 23L, 199L));
        items.add(new ChangeSetItem(1L, 2L, 45L, 25L));
        items.add(new ChangeSetItem(1L, 3L, 49L, 87L));
        items.add(new ChangeSetItem(2L, 2L, 19L, 39L));
        return items;
    }

    public static List<ProductEntity> getEntities(){
        List<ProductEntity> entities = new ArrayList<>();
        entities.add(new ProductEntity(1L, 2L, 23L, 200L, 200L, "some product 1"));
        entities.add(new ProductEntity(1L, 2L, 45L, 50L, 37L, "some product 2"));
        entities.add(new ProductEntity(1L, 3L, 49L, 100L, 90L, "some product 3"));
        entities.add(new ProductEntity(2L, 2L, 19L, 50L, 46L, "some product 4"));
        return entities;
    }

    public static List<ProductEntity> getOutOfStockEntities(){
        List<ProductEntity> entities = new ArrayList<>();
        entities.add(new ProductEntity(1L, 2L, 23L, 200L, 1L, "some product 1"));
        entities.add(new ProductEntity(1L, 2L, 45L, 50L, 12L, "some product 2"));
        entities.add(new ProductEntity(1L, 3L, 49L, 100L, 3L, "some product 3"));
        entities.add(new ProductEntity(2L, 2L, 19L, 50L, 7L, "some product 4"));
        return entities;
    }

    public static List<FullProductId> getFullIds(){
        List<FullProductId> ids = new ArrayList<>();
        ids.add(new FullProductId(1L, 2L, 23L));
        ids.add(new FullProductId(1L, 2L, 45L));
        ids.add(new FullProductId(1L, 3L, 49L));
        ids.add(new FullProductId(2L, 2L, 19L));
        return ids;
    }

    public static String getRandomJsonString(){
        return "{\"id\":28, \"name\":\"Anton\"}";
    }
}
