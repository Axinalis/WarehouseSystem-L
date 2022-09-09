package com.axinalis.store.data;

import java.util.*;

public class Store {

    private Long storeId;
    private String storeName;
    private Map<Long, List<StoreItem>> goods;

    public Store() {
    }

    public Store(Long storeId, String storeName) {
        this.storeId = storeId;
        this.storeName = storeName;
        this.goods = new HashMap<>();
    }

    public Store(Long storeId, String storeName, Map<Long, List<StoreItem>> goods) {
        this.storeId = storeId;
        this.storeName = storeName;
        this.goods = goods;
    }

    public String getStoreName() {
        return storeName;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }

    public Map<Long, List<StoreItem>> getGoods() {
        return goods;
    }

    public void setGoods(Map<Long, List<StoreItem>> goods) {
        this.goods = goods;
    }

    public Long getStoreId() {
        return storeId;
    }

    public void setStoreId(Long storeId) {
        this.storeId = storeId;
    }

    public boolean containsCategory(Long categoryId){
        return goods.containsKey(categoryId);
    }

    public void createCategories(List<Long> categories){
        for(Long category : categories){
            goods.put(category, new ArrayList<>());
        }
    }

    public List<Long> getCategories(){
        return goods.keySet().stream().toList();
    }

    public List<StoreItem> getStoreItemsByCategory(Long category){
        return goods.get(category);
    }

    public void addNewStoreItem(Long categoryId, String title, Long maxStock){
        List<StoreItem> items = goods.get(categoryId);
        if(items == null){
            throw new RuntimeException("Error while adding new item to store. Category with id " + categoryId + " was not found");
        }
        Long maxId = items.stream()
                .map(StoreItem::getItemId)
                .max((it1, it2) -> (int) (Math.signum(it1 - it2))).orElse(1L);
        items.add(new StoreItem(maxId + 1L, title, categoryId, maxStock, maxStock));
    }
}
