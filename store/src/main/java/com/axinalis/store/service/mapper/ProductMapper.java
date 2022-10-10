package com.axinalis.store.service.mapper;

import com.axinalis.store.changer.ChangeSetItem;
import com.axinalis.store.data.FullProductId;
import com.axinalis.store.data.ProductEntity;

public class ProductMapper {

    public static ProductEntity fromChangeSetItemToEntity(ChangeSetItem item){
        ProductEntity entity = new ProductEntity();
        entity.setStoreId(item.getStoreId());
        entity.setCategoryId(item.getCategoryId());
        entity.setProductId(item.getProductId());
        throw new RuntimeException("not ready");
    }

    public static FullProductId fromChangeSetItemToFullProductId(ChangeSetItem item){
        FullProductId productId = new FullProductId();
        productId.setStoreId(item.getStoreId());
        productId.setCategoryId(item.getCategoryId());
        productId.setProductId(item.getProductId());
        return productId;
    }

}
