package com.axinalis.warehouse.service.subscriber;

import com.axinalis.warehouse.consumer.ChangeSetItem;
import com.axinalis.warehouse.entity.StoreItemEntity;
import com.axinalis.warehouse.repository.StoreItemRepository;
import com.axinalis.warehouse.service.ReportsService;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedInputStream;
import java.util.List;

public class StoreItemsSub implements Subscriber<List<ChangeSetItem>> {

    private static Logger log = LoggerFactory.getLogger(StoreItemsSub.class);

    private Long storeId;

    private String subName;
    private Subscription subscription;
    private StoreItemRepository repository;
    private ReportsService service;

    public StoreItemsSub() {
        this.subName = "Lalala";
    }

    public StoreItemsSub(String subName, Long storeId) {
        this.subName = subName;
        this.storeId = storeId;
    }

    public StoreItemsSub(String subName, StoreItemRepository repository, Long storeId, ReportsService service) {
        this.subName = subName;
        this.repository = repository;
        this.storeId = storeId;
        this.service = service;
    }

    public String getSubName() {
        return subName;
    }

    public void setSubName(String subName) {
        this.subName = subName;
    }

    @Override
    public void onSubscribe(Subscription subscription) {
        this.subscription = subscription;
        log.debug("{}: onSubscribe is called", subName);
        subscription.request(Long.MAX_VALUE);
        log.debug("{}: all items are requested", subName);
    }

    @Override
    public void onNext(List<ChangeSetItem> items) {
        updateData(items);

        for(Long categoryId : repository.searchOutOfStockCategories(storeId)){
            List<StoreItemEntity> list = repository.getItemsByCategory(storeId, categoryId);
            log.warn("Category {} is running out of stock in {} store", categoryId, storeId);
            service.processResponse(list.stream().map(item -> new ChangeSetItem(
                    item.getStoreId(),
                    item.getCategoryId(),
                    item.getProductId(),
                    item.getMaxStock() - item.getCurrentStock())).toList());
            repository.updateCategoryStocks(storeId, categoryId);
        }
    }

    @Override
    public void onError(Throwable throwable) {
        log.error("Error occurred in subscriber with name {}. Error is: {}", subName, throwable.getMessage());
    }

    @Override
    public void onComplete() {
        log.debug(subName + ": onComplete is called");
    }

    private void updateData(List<ChangeSetItem> items){
        log.debug("Sub {} got list of items with {} items", subName, items.size());
        for(ChangeSetItem item : items.stream().filter(item -> item.getStoreId().equals(storeId)).toList()){
            repository.updateCurrentStock(
                    item.getStoreId(),
                    item.getCategoryId(),
                    item.getProductId(),
                    item.getCurrentStock());
        }
        repository.flush();
        log.debug("{} items were updated", items.size());
    }
}
