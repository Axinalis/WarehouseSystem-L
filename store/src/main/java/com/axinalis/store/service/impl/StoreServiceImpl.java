package com.axinalis.store.service.impl;

import com.axinalis.store.changer.ChangeSetItem;
import com.axinalis.store.data.ProductEntity;
import com.axinalis.store.data.StoreRepository;
import com.axinalis.store.service.StoreService;
import com.axinalis.store.service.mapper.ProductMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Optional;

public class StoreServiceImpl implements StoreService {

    private static Logger log = LoggerFactory.getLogger(StoreServiceImpl.class);

    private StoreRepository repository;

    public StoreServiceImpl(@Autowired StoreRepository repository) {
        this.repository = repository;
    }

    @Override
    public void updateStocks(List<ChangeSetItem> items) {
        items.forEach(item -> {
            Optional<ProductEntity> entity =
                    repository.findById(
                            ProductMapper.fromChangeSetItemToFullProductId(item));

            if(entity.isPresent()){
                Long oldStock = entity.get().getCurrentStock();
                entity.get().setCurrentStock(oldStock + item.getCurrentStock());
                repository.save(entity.get());
            } else {
                log.warn("Product item with id {}:{}:{} (storeId:categoryId:productId) was not found. Skipping item.",
                        item.getStoreId(), item.getCategoryId(), item.getProductId());
            }
        });
    }
}
