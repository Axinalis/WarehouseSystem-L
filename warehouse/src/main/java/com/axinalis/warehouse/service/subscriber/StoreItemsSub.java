package com.axinalis.warehouse.service.subscriber;

import com.axinalis.warehouse.consumer.ChangeSetItem;
import com.axinalis.warehouse.repository.StoreItemRepository;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class StoreItemsSub implements Subscriber<List<ChangeSetItem>> {

    private static Logger log = LoggerFactory.getLogger(StoreItemsSub.class);
    private String subName;
    private Subscription subscription;
    private StoreItemRepository repository;

    public StoreItemsSub() {
        this.subName = "Lalala";
    }

    public StoreItemsSub(String subName) {
        this.subName = subName;
    }

    public StoreItemsSub(String subName, StoreItemRepository repository) {
        this.subName = subName;
        this.repository = repository;
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
        subscription.request(1);
        log.debug("{}: 1 items is requested", subName);
    }

    @Override
    public void onNext(List<ChangeSetItem> items) {
        this.subscription.request(1);
        log.debug("Sub {} got list of items with {} items", subName, items.size());
        for(ChangeSetItem item : items){
            repository.updateCurrentStock(
                    item.getStoreId(),
                    item.getCategoryId(),
                    item.getProductId(),
                    item.getCurrentStock());
        }

    }

    @Override
    public void onError(Throwable throwable) {
        log.debug("Error occurred in subscriber with name {}. Error is: {}", subName, throwable.getMessage());
    }

    @Override
    public void onComplete() {
        log.debug(subName + ": onComplete is called");
    }
}
