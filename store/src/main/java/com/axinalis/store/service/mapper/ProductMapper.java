package com.axinalis.store.service.mapper;

import com.axinalis.store.changer.ChangeSetItem;
import com.axinalis.store.data.FullProductId;

public class ProductMapper {

    public static FullProductId fromChangeSetItemToFullProductId(ChangeSetItem item){
        FullProductId productId = new FullProductId();
        productId.setStoreId(item.getStoreId());
        productId.setCategoryId(item.getCategoryId());
        productId.setProductId(item.getProductId());
        return productId;
    }

}
