package com.axinalis.store.data;

import com.axinalis.store.changer.ChangeSetItem;
import com.axinalis.store.loader.InitialLoader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class Database {

    private static Logger log = LoggerFactory.getLogger(Database.class);
    private static final Integer defaultBuyingFrequency = 7;

    @Autowired
    private InitialLoader loader;
    @Value("${store.category.all}")
    private String allCategories;
    private List<Store> stores;
    private List<CategoryItem> categories;

    public Database() {
        this.stores = new ArrayList<>();
        this.categories = new ArrayList<>();
    }

    public List<Store> getStores() {
        return stores;
    }

    public void setStores(List<Store> stores) {
        this.stores = stores;
    }

    public Integer getFrequencyOfCategory(String category){
        for(CategoryItem item : categories){
            if(item.getCategoryName().equals(category)){
                return item.getBuyingFrequency();
            }
        }
        log.warn("The category {} wasn't found in database class. Returning 0", category);
        return 0;
    }

    public Integer getFrequencyOfCategory(Long categoryId){
        for(CategoryItem item : categories){
            if(item.getCategoryId().equals(categoryId)){
                return item.getBuyingFrequency();
            }
        }
        log.warn("The category with id {} wasn't found in database class. Returning 0", categoryId);
        return 0;
    }

    public int getNumberOfStores(){
        return stores.size();
    }

    public Long getCategoryId(String category){
        for(CategoryItem item : categories){
            if(item.getCategoryName().equals(category)){
                return item.getCategoryId();
            }
        }
        log.warn("The category {} wasn't found in database class", category);
        throw new RuntimeException("The category wasn't found in database");
    }

    public String getCategoryName(Long id){
        for(CategoryItem item : categories){
            if(item.getCategoryId().equals(id)){
                return item.getCategoryName();
            }
        }
        log.warn("The category with id {} wasn't found in database class", id);
        throw new RuntimeException("The category wasn't found in database");
    }

    public void refillStocks(List<ChangeSetItem> items){
        for(ChangeSetItem item : items){
            Store store = stores
                    .stream()
                    .filter(str -> str.getStoreId().equals(item.getStoreId()))
                    .findFirst()
                    .orElseThrow();

            for(StoreItem storeItem : store.getGoods().get(item.getCategoryId())){
                storeItem.increaseCurrentStockBy(item.getCurrentStock());
            }
        }
    }

    @PostConstruct
    private void initializeStores(){
        initializeCategories();

        //Loading store.properties file and running cycle through all stores
        Map<String, List<String>> storesCategories = loader.listStoresCategories();
        //Map<String, List<Long>> storesCategories = mapLongToString(mapOfStoresCategories);
        for(String storeName : storesCategories.keySet()){

            Store store = addNewStore(storeName, storesCategories.get(storeName)
                    .stream().map(this::getCategoryId).collect(Collectors.toList()));

            //Cycle through every category in store and pull all goods in within current category
            for(Long category : store.getCategories()){
                Map<String, Integer> products = loader.initiateCategory(getCategoryName(category));

                extractBuyingFrequency(getCategoryName(category), products);

                for(String productName : products.keySet()){
                    store.addNewStoreItem(
                            getCategoryId(getCategoryName(category)),
                            productName,
                            Long.valueOf(products.get(productName)));
                }

            }
        }
    }

    private void initializeCategories(){
        List<String> categoriesNames = List.of(allCategories.split(","));
        int categoriesNumber = categoriesNames.size();
        for(int i = 0; i < categoriesNumber; i++){
            this.categories.add(new CategoryItem((long) i, categoriesNames.get(i), defaultBuyingFrequency));
        }
    }

    private Store addNewStore(String storeName, List<Long> categoriesNames){
        Long storeId = stores.stream()
                .map(Store::getStoreId)
                .max((it1, it2) -> (int) (Math.signum(it1 - it2)))
                .orElse(1L);
        Store newStore = new Store(storeId + 1L, storeName);
        newStore.createCategories(categoriesNames);
        stores.add(newStore);
        return newStore;
    }

    private void extractBuyingFrequency(String category, Map<String, Integer> products){
        if(products.containsKey("buyingFrequency")){
            for(CategoryItem item : categories){
                if(item.getCategoryName().equals(category)){
                    item.setBuyingFrequency(products.get("buyingFrequency"));
                    //break;?
                }
            }
            products.remove("buyingFrequency");
        } else {
            log.warn("Buying frequency value for category {} wasn't found. Setting frequency to default ({}).",
                    category, defaultBuyingFrequency);
        }
    }
}
